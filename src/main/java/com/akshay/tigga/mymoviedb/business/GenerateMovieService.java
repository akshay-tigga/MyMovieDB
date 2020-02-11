package com.akshay.tigga.mymoviedb.business;

import com.akshay.tigga.mymoviedb.data.model.Movie;
import com.akshay.tigga.mymoviedb.data.repository.MovieFetchRepository;
import com.akshay.tigga.mymoviedb.data.repository.MovieInsertRepository;
import com.akshay.tigga.mymoviedb.util.DataScrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GenerateMovieService {
    @Autowired
    DataScrapper dataScrapper;

    @Autowired
    private MovieInsertRepository movieInsertRepository;

    @Autowired
    private MovieFetchRepository movieFetchRepository;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public void populateDb() {
        //dataScrapper.populateDbFast();
        dataScrapper.populateDbSlow();
    }

    public void insertMovie(Movie movie) {
        // insert into db
        logger.info("Insert movie: " + movie);

        // insert basic movie details, get m_id back
        int mId = movieInsertRepository.insertBasicMovieData(movie.getBasicMovieData());
        logger.info("mId = " + mId);

        // check if actor is present in actor table or not. if not insert. get id back, use it to populate res table
        for (String actor: movie.getActors()) {
            Integer aId = movieFetchRepository.getActorId(actor);
            if (aId == null) {
                aId = movieInsertRepository.insertActorDetails(actor);
            }
            movieInsertRepository.insertIntoMovieActorResTable(mId, aId);
        }

        for (String producer: movie.getProducers()) {
            Integer pId = movieFetchRepository.getProducerId(producer);
            if (pId == null) {
                pId = movieInsertRepository.insertProducerDetails(producer);
            }
            movieInsertRepository.insertIntoMovieProducerResTable(mId, pId);
        }

        for (String prodHouse: movie.getProductionHouses()) {
            Integer phId = movieFetchRepository.getProductionHouseId(prodHouse);
            if (phId == null) {
                phId = movieInsertRepository.insertProductionHouseDetails(prodHouse);
            }
            movieInsertRepository.insertIntoMovieProductionHouseResTable(mId, phId);
        }

        for (String director: movie.getDirectors()) {
            Integer dId = movieFetchRepository.getDirectorId(director);
            if (dId == null) {
                dId = movieInsertRepository.insertDirectorDetails(director);
            }
            movieInsertRepository.insertIntoMovieDirectorResTable(mId, dId);
        }

        for (String musicComp: movie.getMusicComposers()) {
            Integer mcId = movieFetchRepository.getMusicComposerId(musicComp);
            if (mcId == null) {
                mcId = movieInsertRepository.insertMusicComposerDetails(musicComp);
            }
            movieInsertRepository.insertIntoMovieMusicComposerResTable(mId, mcId);
        }

        for (String language: movie.getLanguages()) {
            Integer lId = movieFetchRepository.getLanguageId(language);
            if (lId == null) {
                lId = movieInsertRepository.insertLanguageDetails(language);
            }
            movieInsertRepository.insertIntoMovieLanguageResTable(mId, lId);
        }

        for (String category: movie.getCategories()) {
            Integer cId = movieFetchRepository.getCategoryId(category);
            if (cId == null) {
                cId = movieInsertRepository.insertCategoryDetails(category);
            }
            movieInsertRepository.insertIntoMovieCategoryResTable(mId, cId);
        }
    }
}