package com.akshay.tigga.mymoviedb.data.repository;

import com.akshay.tigga.mymoviedb.data.model.BasicMovieData;
import com.akshay.tigga.mymoviedb.data.model.SimilarMovieData;

import java.util.List;

public interface MovieFetchRepository {
    Integer getMovieId(String movieTitle);

    String getMovieTitle(int movieId);

    Integer getActorId(String actor);

    Integer getProducerId(String producer);

    Integer getProductionHouseId(String productionHouse);

    Integer getDirectorId(String director);

    Integer getMusicComposerId(String musicComposer);

    Integer getLanguageId(String language);

    Integer getCategoryId(String category);

    List<String> getAllMovieTitles(String sqlQuery);

    List<String> getActorsForMovieTitle(String title);

    List<String> getProducersForMovieTitle(String title);

    List<String> getProdHousesForMovieTitle(String title);

    List<String> getDirectorsForMovieTitle(String title);

    List<String> getMusicCompsForMovieTitle(String title);

    List<String> getLanguagesForMovieTitle(String title);

    List<String> getCategoriesForMovieTitle(String title);

    BasicMovieData getBasicMovieDataForTitle(String title);

    List<SimilarMovieData> getSimilarMovies(String query);
}
