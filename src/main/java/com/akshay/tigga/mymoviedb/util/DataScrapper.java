package com.akshay.tigga.mymoviedb.util;

import com.akshay.tigga.mymoviedb.business.GenerateMovieService;
import com.akshay.tigga.mymoviedb.data.model.BasicMovieData;
import com.akshay.tigga.mymoviedb.data.model.Movie;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.concurrent.TimeUnit;

@Component
public class DataScrapper {
    @Autowired
    private GenerateMovieService generateMovieService;

    @Autowired
    Utility utility;

    private static Logger logger = LoggerFactory.getLogger(DataScrapper.class);

    @Value("${movies.to.fetch.dynamically}")
    private int TOTAL_MOVIES_TO_FETCH = 100;
    private static final int MAX_SUMMARY_LENGTH = 9000;
    private static final int MAX_ATTEMPTS_TO_CONNECT = 20;
    private static final String MOVIEDB_URL = "https://www.themoviedb.org/discover/movie?page=%d&sort_by=popularity.desc";
    private static final String WIKI_MOVIE_URL = "https://en.wikipedia.org/w/api.php?action=parse&section=0&prop=text&format=json&page=%s";
    private static final String WIKI_MOVIE_CATEGORY_URL = "https://en.wikipedia.org/w/api.php?action=parse&prop=wikitext&format=json&page=%s";

    private static String cleanString(String input) {
        return input.replaceAll("\\[[0-9]*\\]", "");
    }

    private static String getSingleData(Element element) {
        List<String> values = new ArrayList<>();
        element.children().forEach(child -> {
            String cleanedString = cleanString(child.text());
            if (!cleanedString.isEmpty()) {
                values.add(cleanedString);
            }
        });
        if (values.size() == 0) return "dummy";
        return values.get(values.size()-1);
    }

    private static List<String> getListData(Element element) {
        List<String> values = new ArrayList<>();
        Elements dataElements = element.select("li");
        if (dataElements.size() == 0) {
            dataElements = element.select("td");
        }
        dataElements.forEach(dataElement -> {
            String cleanedString = cleanString(dataElement.text());
            if (!cleanedString.isEmpty()) {
                values.add(cleanedString);
            }
        });
        return values;
    }

    private static List<String> getLanguages(Element element) {
        List<String> values = new ArrayList<>();
        element.children().forEach(child -> {
            String cleanedString = cleanString(child.text());
            if (!cleanedString.isEmpty()) {
                values.add(cleanString(child.text()));
            }
        });
        values.remove(0);
        return values;
    }

    private static String getReleaseDate(Element element) {
        Document document = Jsoup.parse(element.html());
        String relDate = document.select("span").first().text();
        relDate = relDate.replaceAll(" ", "").replaceAll("\\(", "")
                .replaceAll("\\)", "");
        relDate = cleanString(relDate.trim());
        relDate = relDate.substring(1);
        return relDate;
    }

    public void populateDbFast() {
        logger.info("Beginning of data scrapping ---");
        Long beginTime = System.currentTimeMillis();

        List<String> movieList = utility.getStoredMovieList();
        for (String movie : movieList) {
            Movie fetchedMovie = fetchMovieDetails(movie);
            if (fetchedMovie != null) {
                logger.info("Fetched movie details successfully for movie " + movie);
                generateMovieService.insertMovie(fetchedMovie);
            } else {
                logger.error("Failed to fetch movie details for movie " + movie);
            }
        }

        logger.info("--- Data scrapping completed");
        Long endTime = System.currentTimeMillis();
        logger.info("Time taken = " + TimeUnit.MILLISECONDS.toMinutes(endTime-beginTime) +
                " mins(" + TimeUnit.MILLISECONDS.toSeconds(endTime-beginTime) + " secs)");
    }

    public void populateDbSlow() {
        logger.info("Movies to fetch = " + TOTAL_MOVIES_TO_FETCH);
        logger.info("Beginning of data scrapping ---");
        Long beginTime = System.currentTimeMillis();

        RestTemplate restTemplate = new RestTemplate();
        Set<String> movieList = new HashSet<>();
        int page = 0, attempt = 0;
        outer: while (movieList.size() < TOTAL_MOVIES_TO_FETCH) {
            String movieNameFetchUrl = String.format(MOVIEDB_URL, ++page);
            try {
                attempt++;
                ResponseEntity<String> responseEntity = restTemplate.getForEntity(movieNameFetchUrl, String.class);
                if (responseEntity.getStatusCode().equals(HttpStatus.OK) && responseEntity.getBody() != null) {
                    Document document = Jsoup.parse(responseEntity.getBody());
                    logger.info("Fetched page " + page + " from " + movieNameFetchUrl);
                    Elements elements = document.select("a").select(".result");
                    for (Element element : elements) {
                        String movieName = element.attr("title");
                        if (movieName != null && !movieName.isEmpty() && !movieList.contains(movieName)) {
                            Movie fetchedMovie = fetchMovieDetails(movieName);
                            if (fetchedMovie != null) {
                                logger.info("Fetched movie details successfully for movie " + movieName);
                                generateMovieService.insertMovie(fetchedMovie);
                                movieList.add(movieName);
                                if (movieList.size() >= TOTAL_MOVIES_TO_FETCH) {
                                    break outer;
                                }
                            } else {
                                logger.error("Failed to fetch movie details for movie " + movieName);
                            }
                        }
                    }
                } else {
                    logger.error("Failed to get movie names from url " + movieNameFetchUrl + " HttpStatus:" +
                            responseEntity.getStatusCode());
                }
                attempt = 0; // reset num of attempts
            } catch (RestClientException ex) {
                logger.error("Failed to get movie names from url " + movieNameFetchUrl + " Attempt = " + attempt + " Exception: " + ex);
                if (attempt > MAX_ATTEMPTS_TO_CONNECT) {
                    logger.error("Maximum attempts to connect exceeded. Database not initialized. Please check your internet connection.");
                    break;
                }
            }
        }

        logger.info("--- Data scrapping completed");
        logger.info("Total movies fetched = " + movieList.size());
        Long endTime = System.currentTimeMillis();
        logger.info("Time taken = " + TimeUnit.MILLISECONDS.toMinutes(endTime-beginTime) +
                " mins(" + TimeUnit.MILLISECONDS.toSeconds(endTime-beginTime) + " secs)");
    }

    private static Movie fetchMovieDetails(String movieName) {
        RestTemplate restTemplate = new RestTemplate();
        String movieDetailsFetchUrl = String.format(WIKI_MOVIE_URL, movieName);
        try {
            ResponseEntity<String> responseEntity = restTemplate.getForEntity(movieDetailsFetchUrl, String.class);
            if (responseEntity.getStatusCode().equals(HttpStatus.OK) && responseEntity.getBody() != null) {
                ObjectMapper objectMapper = new ObjectMapper();
                try {
                    JsonNode root = objectMapper.readTree(responseEntity.getBody());
                    String responseHtml = root.path("parse").path("text").path("*").asText();
                    if (!responseHtml.isEmpty()) {
                        Document document = Jsoup.parse(responseHtml);
                        return fetchMovieDetailsUtil(document, movieName);
                    } else {
                        logger.error("Failed to get movie details from url " + movieDetailsFetchUrl + " Got empty html");
                    }
                } catch (JsonProcessingException ex) {
                    logger.error("Failed to process json data for url " + movieDetailsFetchUrl + " Exception: " + ex);
                }
            } else {
                logger.error("Failed to get movie details from url " + movieDetailsFetchUrl + " HttpStatus:" +
                        responseEntity.getStatusCode());
            }
        } catch (RestClientException ex) {
            logger.error("Failed to get movie details from url " + movieDetailsFetchUrl + " Exception: " + ex);
        }
        return null;
    }

    private static Movie fetchMovieDetailsUtil(Document document, String movieName) {
        Movie movie = new Movie();
        BasicMovieData basicMovieData = new BasicMovieData();
        basicMovieData.setTitle(movieName);

        boolean actors = false, directors = false, producers = false, musicComp = false, prodHouse = false,
                relDate = false, duration = false, language = false, budget = false, boxOfficeColl = false;

        Element infoTable = document.select("table").first();
        try {
            for(Element element : infoTable.select("tr")) {
                Element thead = element.select("th").first();
                if (thead != null) {
                    switch (thead.text()) {
                        case "Starring" :
                            actors = true;
                            movie.setActors(getListData(element));
                            break;
                        case "Directed by" :
                            directors = true;
                            movie.setDirectors(getListData(element));
                        case "Produced by" :
                            producers = true;
                            movie.setProducers(getListData(element));
                        case "Music by" :
                            musicComp = true;
                            movie.setMusicComposers(getListData(element));
                            break;
                        case "Production company" :
                        case "Production companies" :
                            prodHouse = true;
                            movie.setProductionHouses(getListData(element));
                            break;
                        case "Release date" :
                            String releaseDate = getReleaseDate(element);
                            if (Utility.isValidDate(releaseDate)) {
                                relDate = true;
                                basicMovieData.setReleaseDate(releaseDate);
                            }
                            break;
                        case "Running time" :
                            duration = true;
                            basicMovieData.setDuration(getSingleData(element));
                            break;
                        case "Language" :
                            language = true;
                            movie.setLanguages(getLanguages(element));
                            break;
                        case "Budget" :
                            budget = true;
                            basicMovieData.setBudget(getSingleData(element));
                            break;
                        case "Box office" :
                            boxOfficeColl = true;
                            basicMovieData.setCollection(getSingleData(element));
                            break;
                    }
                } else {
                    logger.debug("th is null for " + element);
                }
            }
        } catch (NullPointerException ex) {
            logger.error("Failed to fetch movie details for " + movieName + " Exception: " + ex);
            return null;
        }

        // To increase server startup time
        // Since these fields are not present for a lot of movies
        // And we are not using these fields for any comparison
        if (!budget) {
            basicMovieData.setBudget("Data not available");
        }
        if (!boxOfficeColl) {
            basicMovieData.setCollection("Data not available");
        }

        if (actors && directors && producers && musicComp && prodHouse && relDate && duration && language) {
            // first paragraph
            for (Element element : document.select("p")) {
                if (!element.text().isEmpty()) {
                    String summary = cleanString(element.text());
                    if (summary.length() > MAX_SUMMARY_LENGTH) {
                        summary = summary.substring(0, MAX_SUMMARY_LENGTH);
                    }
                    basicMovieData.setSummary(summary);
                    break;
                }
            }

            List<String> categories = getCategoriesForMovie(movieName);
            movie.setCategories(categories);  // add categories
            basicMovieData.setCategoryCount(categories.size());  // add category number

            // return movie object
            movie.setBasicMovieData(basicMovieData);
            return movie;
        } else {
            logger.error(movieName + " is not a movie");
            logger.error("actors:" + actors + " directors:" + directors + " producers:" + producers + " musicComp:" + musicComp +
                    " productionHouse:" + prodHouse + " releaseDate:" + relDate + " duration:" + duration + " language:" + language);
            return null;
        }
    }

    private static List<String> getCategoriesForMovie(String movieName) {
        String movieCategoryFetchUrl = String.format(WIKI_MOVIE_CATEGORY_URL, movieName);
        RestTemplate restTemplate = new RestTemplate();
        try {
            ResponseEntity<String> responseEntity = restTemplate.getForEntity(movieCategoryFetchUrl, String.class);
            if (responseEntity.getStatusCode().equals(HttpStatus.OK) && responseEntity.getBody() != null) {
                ObjectMapper objectMapper = new ObjectMapper();
                try {
                    JsonNode root = objectMapper.readTree(responseEntity.getBody());
                    String responseWikiText = root.path("parse").path("wikitext").path("*").asText();
                    if (!responseWikiText.isEmpty()) {
                        responseWikiText = responseWikiText.substring(responseWikiText.indexOf("[[Category:"));
                        responseWikiText = responseWikiText.replaceAll("Category:", "");
                        responseWikiText = responseWikiText.replaceAll("]]", "");
                        responseWikiText = responseWikiText.replaceAll("\\[", "");

                        List<String> categoryList = new ArrayList<>();
                        String[] categories = responseWikiText.split("\n");
                        for (String category : categories) {
                            categoryList.add(cleanString(category));
                        }
                        return categoryList;
                    } else {
                        logger.error("Failed to get categories for movie " + movieName + " url: " + movieCategoryFetchUrl +
                                " Wiki text response is empty");
                    }
                } catch (JsonProcessingException ex) {
                    logger.error("Failed to get categories for movie " + movieName + " url: " + movieCategoryFetchUrl +
                            " Exception: " + ex);
                }
            } else {
                logger.error("Failed to get categories for movie " + movieName + " url: " + movieCategoryFetchUrl +
                        " HttpStatus: " + responseEntity.getStatusCode());
            }
        } catch (RestClientException ex) {
            logger.error("Failed to get categories for movie " + movieName + " url: " + movieCategoryFetchUrl +
                    " Exception: " + ex);
        }
        return Collections.emptyList();
    }

}
