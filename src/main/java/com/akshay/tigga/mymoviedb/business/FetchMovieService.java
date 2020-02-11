package com.akshay.tigga.mymoviedb.business;

import com.akshay.tigga.mymoviedb.data.model.BasicMovieData;
import com.akshay.tigga.mymoviedb.data.model.Movie;
import com.akshay.tigga.mymoviedb.data.model.SimilarMovieData;
import com.akshay.tigga.mymoviedb.data.repository.MovieFetchRepository;
import com.akshay.tigga.mymoviedb.util.Utility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
public class FetchMovieService {
    @Autowired
    private MovieFetchRepository movieFetchRepository;

    public List<String> getAllMovieTitles(String sort) {
        String query = Utility.getQueryForMovieTitles(sort);
        return movieFetchRepository.getAllMovieTitles(query);
    }

    public Movie getMovieForTitle(String title) {
        BasicMovieData basicMovieData = movieFetchRepository.getBasicMovieDataForTitle(title);
        if (basicMovieData == null) {
            return null;
        }
        Movie movie = new Movie();
        movie.setBasicMovieData(basicMovieData);
        movie.setActors(movieFetchRepository.getActorsForMovieTitle(title));
        movie.setProducers(movieFetchRepository.getProducersForMovieTitle(title));
        movie.setProductionHouses(movieFetchRepository.getProdHousesForMovieTitle(title));
        movie.setDirectors(movieFetchRepository.getDirectorsForMovieTitle(title));
        movie.setMusicComposers(movieFetchRepository.getMusicCompsForMovieTitle(title));
        movie.setLanguages(movieFetchRepository.getLanguagesForMovieTitle(title));
        movie.setCategories(movieFetchRepository.getCategoriesForMovieTitle(title));
        return movie;
    }

    public List<SimilarMovieData> getSimilarMovies(String title, int limit) {
        Integer movieId = movieFetchRepository.getMovieId(title);
        if (movieId == null) {
            return null;
        }
        String query = Utility.getQueryForSimilarMovies(movieId, limit);
        List<SimilarMovieData> similarMovies = movieFetchRepository.getSimilarMovies(query);
        if (similarMovies == null) {
            return Collections.emptyList();
        }
        return similarMovies;
    }
}
