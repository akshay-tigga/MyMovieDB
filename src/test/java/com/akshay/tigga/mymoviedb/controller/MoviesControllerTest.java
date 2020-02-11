package com.akshay.tigga.mymoviedb.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.akshay.tigga.mymoviedb.business.FetchMovieService;
import com.akshay.tigga.mymoviedb.data.model.BasicMovieData;
import com.akshay.tigga.mymoviedb.data.model.Movie;
import com.akshay.tigga.mymoviedb.data.model.SimilarMovieData;
import com.akshay.tigga.mymoviedb.util.Utility;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;

@RunWith(SpringRunner.class)
@WebMvcTest(MoviesController.class)
public class MoviesControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FetchMovieService fetchMovieService;

    @Test
    public void moviesControllerBasicTest() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders.get("/");
        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().string(Utility.getApiInfo()));
    }

    @Test
    public void moviesControllerListMoviesTest() throws Exception {
        // return movies by title asc
        when(fetchMovieService.getAllMovieTitles("title.asc"))
                .thenReturn(Arrays.asList(Constants.MOVIE_LIST_TITLE_ASC.split(", ")));

        String expectedResponse = new ObjectMapper().writeValueAsString(
                Arrays.asList(Constants.MOVIE_LIST_TITLE_ASC.split(", ")));

        RequestBuilder request = MockMvcRequestBuilders.get("/movies");
        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().string(expectedResponse));

        // return movies by date desc
        when(fetchMovieService.getAllMovieTitles("date.desc"))
                .thenReturn(Arrays.asList(Constants.MOVIE_LIST_DATE_DESC.split(", ")));

        expectedResponse = new ObjectMapper().writeValueAsString(
                Arrays.asList(Constants.MOVIE_LIST_DATE_DESC.split(", ")));

        request = MockMvcRequestBuilders.get("/movies?sort=date.desc");
        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().string(expectedResponse));
    }

    @Test
    public void moviesControllerGetMovieDetailsTest() throws Exception {
        BasicMovieData basicMovieData = new BasicMovieData(Constants.MOVIE_TITLE, Constants.MOVIE_REL_DATE, Constants.MOVIE_DURATION,
                Constants.MOVIE_BUDGET, Constants.MOVIE_COLLECTION, Constants.MOVIE_SUMMARY, Constants.MOVIE_CATEGORY_COUNT);
        Movie movie = new Movie(basicMovieData, Constants.MOVIE_ACTORS, Constants.MOVIE_PRODUCERS, Constants.MOVIE_PROD_HOUSES,
                Constants.MOVIE_DIRECTORS, Constants.MOVIE_MUSIC_COMP, Constants.MOVIE_LANG, Constants.MOVIE_CATEGORY);

        when(fetchMovieService.getMovieForTitle("Free Solo"))
                .thenReturn(movie);
        String expectedResponse = new ObjectMapper().writeValueAsString(movie);

        RequestBuilder request = MockMvcRequestBuilders.get("/movies/Free Solo");
        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().json(expectedResponse));
    }

    @Test
    public void moviesControllerGetSimilarMoviesTest() throws Exception {
        SimilarMovieData similarMovieData1 = new SimilarMovieData(Constants.FS_TITLE_1, Constants.FS_ACTORS_1, Constants.FS_CATEGORY_1, Constants.FS_TOTAL_1);
        SimilarMovieData similarMovieData2 = new SimilarMovieData(Constants.FS_TITLE_2, Constants.FS_ACTORS_2, Constants.FS_CATEGORY_2, Constants.FS_TOTAL_2);

        when(fetchMovieService.getSimilarMovies("Free Solo", 2))
                .thenReturn(Arrays.asList(similarMovieData1, similarMovieData2));
        String expectedResponse = new ObjectMapper().writeValueAsString(Arrays.asList(similarMovieData1, similarMovieData2));

        RequestBuilder request = MockMvcRequestBuilders.get("/similarmovies?title=Free Solo&limit=2");
        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().json(expectedResponse));
    }

}
