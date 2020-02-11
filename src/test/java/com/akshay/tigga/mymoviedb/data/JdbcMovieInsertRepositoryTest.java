package com.akshay.tigga.mymoviedb.data;

import com.akshay.tigga.mymoviedb.data.model.BasicMovieData;
import com.akshay.tigga.mymoviedb.data.repository.JdbcMovieInsertRepository;
import com.akshay.tigga.mymoviedb.data.repository.MovieFetchRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@JdbcTest
public class JdbcMovieInsertRepositoryTest {
    @InjectMocks
    JdbcMovieInsertRepository jdbcMovieInsertRepository;

    @Mock
    JdbcTemplate jdbcTemplate;

    @Mock
    MovieFetchRepository movieFetchRepository;

    @Test
    public void insertActorDetailsTest() {
        when(jdbcTemplate.update("insert into actors(a_name) values(?)", new Object[]{"Akshay Tigga"}))
                .thenReturn(1);
        jdbcMovieInsertRepository.insertActorDetails("Akshay Tigga");
        verify(movieFetchRepository).getActorId(anyString());
    }

    @Test
    public void insertBasicMovieDataTest() {
        BasicMovieData basicMovieData = new BasicMovieData(Constants.MOVIE_TITLE, Constants.MOVIE_REL_DATE, Constants.MOVIE_DURATION,
                Constants.MOVIE_BUDGET, Constants.MOVIE_COLLECTION, Constants.MOVIE_SUMMARY, Constants.MOVIE_CATEGORY_COUNT);

        when(jdbcTemplate.update("insert into movies(title, release_date, duration, budget, collection, summary, category_count) " +
                "values ( ?, ?, ?, ?, ?, ?, ?)", new Object[]{
                basicMovieData.getTitle(), basicMovieData.getReleaseDate(), basicMovieData.getDuration(),
                basicMovieData.getBudget(), basicMovieData.getCollection(), basicMovieData.getSummary(), basicMovieData.getCategoryCount()
        })).thenReturn(1);

        jdbcMovieInsertRepository.insertBasicMovieData(basicMovieData);
        verify(movieFetchRepository).getMovieId(basicMovieData.getTitle());
    }
}
