package com.akshay.tigga.mymoviedb.business;

import static org.mockito.ArgumentMatchers.anyInt;
import com.akshay.tigga.mymoviedb.data.model.BasicMovieData;
import com.akshay.tigga.mymoviedb.data.model.Movie;
import com.akshay.tigga.mymoviedb.data.repository.MovieFetchRepository;
import com.akshay.tigga.mymoviedb.data.repository.MovieInsertRepository;
import com.akshay.tigga.mymoviedb.util.DataScrapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.*;

/*
Before running this test, make sure than you are loading data dynamically.
That is, you have not commented out
 */

@RunWith(MockitoJUnitRunner.class)
public class GenerateMovieServiceTest {
    @InjectMocks
    private GenerateMovieService generateMovieService;

    @Mock
    private MovieInsertRepository movieInsertRepository;

    @Mock
    private MovieFetchRepository movieFetchRepository;

    @Mock
    private DataScrapper dataScrapper;

    @Test
    public void populateDbTest() {
        generateMovieService.populateDb();
        verify(dataScrapper, times(1)).populateDbFast();
    }

    @Test
    public void insertMovieTest() {
        BasicMovieData basicMovieData = new BasicMovieData(Constants.MOVIE_TITLE, Constants.MOVIE_REL_DATE, Constants.MOVIE_DURATION,
                Constants.MOVIE_BUDGET, Constants.MOVIE_COLLECTION, Constants.MOVIE_SUMMARY, Constants.MOVIE_CATEGORY_COUNT);
        Movie movie = new Movie(basicMovieData, Constants.MOVIE_ACTORS, Constants.MOVIE_PRODUCERS, Constants.MOVIE_PROD_HOUSES,
                Constants.MOVIE_DIRECTORS, Constants.MOVIE_MUSIC_COMP, Constants.MOVIE_LANG, Constants.MOVIE_CATEGORY);

        generateMovieService.insertMovie(movie);
        verify(movieInsertRepository).insertBasicMovieData(basicMovieData);
        for (String actor: Constants.MOVIE_ACTORS)
            verify(movieFetchRepository).getActorId(actor);
        verify(movieInsertRepository, atLeastOnce()).insertIntoMovieActorResTable(anyInt(), anyInt());

        for (String producer: Constants.MOVIE_PRODUCERS)
            verify(movieFetchRepository).getProducerId(producer);
        verify(movieInsertRepository, atLeastOnce()).insertIntoMovieProducerResTable(anyInt(), anyInt());

        for (String prodHouse: Constants.MOVIE_PROD_HOUSES)
            verify(movieFetchRepository).getProductionHouseId(prodHouse);
        verify(movieInsertRepository, atLeastOnce()).insertIntoMovieProductionHouseResTable(anyInt(), anyInt());

        for (String director: Constants.MOVIE_DIRECTORS)
            verify(movieFetchRepository).getDirectorId(director);
        verify(movieInsertRepository, atLeastOnce()).insertIntoMovieDirectorResTable(anyInt(), anyInt());

        for (String musicComp: Constants.MOVIE_MUSIC_COMP)
            verify(movieFetchRepository).getMusicComposerId(musicComp);
        verify(movieInsertRepository, atLeastOnce()).insertIntoMovieMusicComposerResTable(anyInt(), anyInt());

        for (String language: Constants.MOVIE_LANG)
            verify(movieFetchRepository).getLanguageId(language);
        verify(movieInsertRepository, atLeastOnce()).insertIntoMovieLanguageResTable(anyInt(), anyInt());

        for (String category: Constants.MOVIE_CATEGORY)
            verify(movieFetchRepository).getCategoryId(category);
        verify(movieInsertRepository, atLeastOnce()).insertIntoMovieCategoryResTable(anyInt(), anyInt());
    }
}
