package com.akshay.tigga.mymoviedb.data.repository;

import com.akshay.tigga.mymoviedb.data.model.BasicMovieData;

public interface MovieInsertRepository {
    Integer insertBasicMovieData(BasicMovieData basicMovieData);

    Integer insertActorDetails(String actorName);

    Integer insertIntoMovieActorResTable(int mId, int aId);

    Integer insertProducerDetails(String producerName);

    Integer insertIntoMovieProducerResTable(int mId, int pId);

    Integer insertProductionHouseDetails(String productionHouseName);

    Integer insertIntoMovieProductionHouseResTable(int mId, int phId);

    Integer insertDirectorDetails(String directorName);

    Integer insertIntoMovieDirectorResTable(int mId, int dId);

    Integer insertMusicComposerDetails(String musicComposerName);

    Integer insertIntoMovieMusicComposerResTable(int mId, int mcId);

    Integer insertLanguageDetails(String language);

    Integer insertIntoMovieLanguageResTable(int mId, int lId);

    Integer insertCategoryDetails(String category);

    Integer insertIntoMovieCategoryResTable(int mId, int cId);
}
