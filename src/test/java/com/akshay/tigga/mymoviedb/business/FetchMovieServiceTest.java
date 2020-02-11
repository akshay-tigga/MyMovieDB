package com.akshay.tigga.mymoviedb.business;

import com.akshay.tigga.mymoviedb.data.model.BasicMovieData;
import com.akshay.tigga.mymoviedb.data.model.Movie;
import com.akshay.tigga.mymoviedb.data.model.SimilarMovieData;
import com.akshay.tigga.mymoviedb.data.repository.MovieFetchRepository;
import com.akshay.tigga.mymoviedb.util.Utility;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class FetchMovieServiceTest {
    @InjectMocks
    private FetchMovieService fetchMovieService;

    @Mock
    private MovieFetchRepository movieFetchRepository;

    @Test
    public void getAllMovieTitlesTest() {
        // return movies by title asc
        when(movieFetchRepository.getAllMovieTitles("select title from movies order by title asc"))
                .thenReturn(Arrays.asList(Constants.MOVIE_LIST_TITLE_ASC.split(", ")));

        List<String> expectedResult = Arrays.asList(Constants.MOVIE_LIST_TITLE_ASC.split(", "));
        List<String> actualResult = fetchMovieService.getAllMovieTitles("title.asc");
        assertEquals(expectedResult, actualResult);

        // return movies by date desc
        when(movieFetchRepository.getAllMovieTitles("select title from movies order by release_date desc"))
                .thenReturn(Arrays.asList(Constants.MOVIE_LIST_DATE_DESC.split(", ")));

        expectedResult = Arrays.asList(Constants.MOVIE_LIST_DATE_DESC.split(", "));
        actualResult = fetchMovieService.getAllMovieTitles("date.desc");
        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void getMovieForTitleTest() {
        BasicMovieData basicMovieData = new BasicMovieData(Constants.MOVIE_TITLE, Constants.MOVIE_REL_DATE, Constants.MOVIE_DURATION,
                Constants.MOVIE_BUDGET, Constants.MOVIE_COLLECTION, Constants.MOVIE_SUMMARY, Constants.MOVIE_CATEGORY_COUNT);
        Movie expectedResult = new Movie(basicMovieData, Constants.MOVIE_ACTORS, Constants.MOVIE_PRODUCERS, Constants.MOVIE_PROD_HOUSES,
                Constants.MOVIE_DIRECTORS, Constants.MOVIE_MUSIC_COMP, Constants.MOVIE_LANG, Constants.MOVIE_CATEGORY);

        when(movieFetchRepository.getBasicMovieDataForTitle("Free Solo"))
                .thenReturn(basicMovieData);
        when(movieFetchRepository.getActorsForMovieTitle("Free Solo"))
                .thenReturn(Constants.MOVIE_ACTORS);
        when(movieFetchRepository.getProducersForMovieTitle("Free Solo"))
                .thenReturn(Constants.MOVIE_PRODUCERS);
        when(movieFetchRepository.getProdHousesForMovieTitle("Free Solo"))
                .thenReturn(Constants.MOVIE_PROD_HOUSES);
        when(movieFetchRepository.getDirectorsForMovieTitle("Free Solo"))
                .thenReturn(Constants.MOVIE_DIRECTORS);
        when(movieFetchRepository.getMusicCompsForMovieTitle("Free Solo"))
                .thenReturn(Constants.MOVIE_MUSIC_COMP);
        when(movieFetchRepository.getLanguagesForMovieTitle("Free Solo"))
                .thenReturn(Constants.MOVIE_LANG);
        when(movieFetchRepository.getCategoriesForMovieTitle("Free Solo"))
                .thenReturn(Constants.MOVIE_CATEGORY);

        Movie actualResult = fetchMovieService.getMovieForTitle("Free Solo");
        assertEquals(expectedResult.toString(), actualResult.toString());
    }

    @Test
    public void getSimilarMoviesTest() {
        SimilarMovieData similarMovieData1 = new SimilarMovieData(Constants.FS_TITLE_1, Constants.FS_ACTORS_1, Constants.FS_CATEGORY_1, Constants.FS_TOTAL_1);
        SimilarMovieData similarMovieData2 = new SimilarMovieData(Constants.FS_TITLE_2, Constants.FS_ACTORS_2, Constants.FS_CATEGORY_2, Constants.FS_TOTAL_2);

        when(movieFetchRepository.getMovieId("Free Solo"))
                .thenReturn(4);
        String query = Utility.getQueryForSimilarMovies(4, 2);
        when(movieFetchRepository.getSimilarMovies(query))
                .thenReturn(Arrays.asList(similarMovieData1, similarMovieData2));

        List<SimilarMovieData> expectedResult = Arrays.asList(similarMovieData1, similarMovieData2);
        List<SimilarMovieData> actualResult = fetchMovieService.getSimilarMovies("Free Solo", 2);
        assertEquals(expectedResult, actualResult);
    }
}
