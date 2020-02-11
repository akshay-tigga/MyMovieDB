package com.akshay.tigga.mymoviedb.data;

import com.akshay.tigga.mymoviedb.data.repository.JdbcMovieFetchRepository;
import com.akshay.tigga.mymoviedb.util.Utility;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

/*
Before running this test make sure to use 'data.sql' file present in 'src/test/resources/data.sql'.
Make sure to uncomment all the commands.

After running this test, make sure to return 'src/test/resources/data.sql' to it's original state.
That is, everything should be commented out except the first line/command(since a script file cannot be empty).
This is required so that 'MoviesControllerIntegrationTest' runs without any issue.
 */

@RunWith(SpringRunner.class)
@JdbcTest
public class JdbcMovieFetchRepositoryTest {
    @InjectMocks
    JdbcMovieFetchRepository jdbcMovieFetchRepository;

    @Mock
    JdbcTemplate jdbcTemplate;

    @Autowired
    JdbcTemplate jdbcTemplateAutoWired;

    @Test
    public void getAllMovieTitlesTest() {
        // return all movie titles order by title ascending
        String query = Utility.getQueryForMovieTitles("title.asc");
        when(jdbcTemplate.queryForList(query, String.class))
                .thenReturn(jdbcTemplateAutoWired.queryForList(query, String.class));

        List<String> actualResult = jdbcMovieFetchRepository.getAllMovieTitles(query);
        List<String> expectedResult = Arrays.asList(Constants.ALL_MOVIES_TITLE_ASC.split(", "));
        assertEquals(expectedResult, actualResult);

        // return all movie titles order by release date descending
        query = Utility.getQueryForMovieTitles("date.desc");
        when(jdbcTemplate.queryForList(query, String.class))
                .thenReturn(jdbcTemplateAutoWired.queryForList(query, String.class));

        actualResult = jdbcMovieFetchRepository.getAllMovieTitles(query);
        expectedResult = Arrays.asList(Constants.ALL_MOVIES_DATE_DESC.split(", "));
        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void getActorsForMovieTitleTest() {
        when(jdbcTemplate.queryForList("select a_name from actors where a_id in " +
                "(select a_id from movie_actor_res where m_id in " +
                "(select m_id from movies where title=?));", new Object[]{"Hell Fest"}, String.class))
                .thenReturn(jdbcTemplateAutoWired.queryForList("select a_name from actors where a_id in " +
                        "(select a_id from movie_actor_res where m_id in " +
                        "(select m_id from movies where title=?));", new Object[]{"Hell Fest"}, String.class));

        List<String> actualResult = jdbcMovieFetchRepository.getActorsForMovieTitle("Hell Fest");
        List<String> expectedResult = Arrays.asList(Constants.HF_ACTORS.split(", "));
        assertEquals(expectedResult, actualResult);
    }
}
