package com.akshay.tigga.mymoviedb.controller;

import com.akshay.tigga.mymoviedb.data.model.BasicMovieData;
import com.akshay.tigga.mymoviedb.data.model.Movie;
import com.akshay.tigga.mymoviedb.data.model.SimilarMovieData;
import com.akshay.tigga.mymoviedb.data.model.SimilarMovieDataRowMapper;
import com.akshay.tigga.mymoviedb.util.Utility;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.skyscreamer.jsonassert.JSONAssert;

import java.util.List;

import static org.junit.Assert.assertEquals;

/*
Before running this test make sure to comment out all the commands in 'src/test/resources/data.sql'
except for the first dummy command (since a script file cannot be empty).

The 'data.sql' file in 'src/test/resources/data.sql' has dummy test data which is used
in 'JdbcMovieFetchRepositoryTest'
 */

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MoviesControllerIntegrationTest {
    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Test
    public void moviesControllerBasicTest() {
        String actualResponse = testRestTemplate.getForObject("/", String.class);
        String expectedResponse = Utility.getApiInfo();
        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    public void moviesControllerListMoviesTest() {
        List<String> actualResponse = testRestTemplate.getForObject("/movies", List.class);
        List<String> expectedResponse = jdbcTemplate.queryForList(Utility.getQueryForMovieTitles("title.asc"), String.class);
        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    public void moviesControllerGetMovieDetailsTest() {
        BasicMovieData basicMovieData = new BasicMovieData(Constants.MOVIE_TITLE, Constants.MOVIE_REL_DATE, Constants.MOVIE_DURATION,
                Constants.MOVIE_BUDGET, Constants.MOVIE_COLLECTION, Constants.MOVIE_SUMMARY, Constants.MOVIE_CATEGORY_COUNT);
        Movie movie = new Movie(basicMovieData, Constants.MOVIE_ACTORS, Constants.MOVIE_PRODUCERS, Constants.MOVIE_PROD_HOUSES,
                Constants.MOVIE_DIRECTORS, Constants.MOVIE_MUSIC_COMP, Constants.MOVIE_LANG, Constants.MOVIE_CATEGORY);

        Movie actualResponse = testRestTemplate.getForObject("/movies/Free Solo", Movie.class);
        assertEquals(movie.toString(), actualResponse.toString());
    }

    @Test
    public void moviesControllerGetSimilarMoviesTest() throws Exception {
        Integer mId = jdbcTemplate.queryForObject("select m_id from movies where title=?", new Object[]{"Free Solo"}, Integer.class);
        List<SimilarMovieData> expectedMovieList = jdbcTemplate.query(Utility.getQueryForSimilarMovies(mId, 0), new SimilarMovieDataRowMapper());
        for (SimilarMovieData movie : expectedMovieList) {
            String title = jdbcTemplate.queryForObject("select title from movies where m_id=?", new Object[]{movie.getMovieId()}, String.class);
            movie.setMovieTitle(title);
        }

        List<SimilarMovieData> actualList = testRestTemplate.getForObject("/similarmovies?title=Free Solo", List.class);

        String expectedResponse = new ObjectMapper().writeValueAsString(expectedMovieList);
        String actualResponse = new ObjectMapper().writeValueAsString(actualList);
        JSONAssert.assertEquals(expectedResponse, actualResponse, false);
    }
}
