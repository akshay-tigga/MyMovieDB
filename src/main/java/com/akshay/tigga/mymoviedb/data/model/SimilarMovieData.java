package com.akshay.tigga.mymoviedb.data.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class SimilarMovieData {
    @JsonIgnore
    private int movieId;
    private String movieTitle;
    private int matchingActorCount;
    private int matchingCategoryCount;
    private int totalMatchCount;

    public SimilarMovieData() {
    }

    public SimilarMovieData(String movieTitle, int matchingActorCount, int matchingCategoryCount, int totalMatchCount) {
        this.movieTitle = movieTitle;
        this.matchingActorCount = matchingActorCount;
        this.matchingCategoryCount = matchingCategoryCount;
        this.totalMatchCount = totalMatchCount;
    }

    public SimilarMovieData(int movieId, String movieTitle, int matchingActorCount, int matchingCategoryCount, int totalMatchCount) {
        this.movieId = movieId;
        this.movieTitle = movieTitle;
        this.matchingActorCount = matchingActorCount;
        this.matchingCategoryCount = matchingCategoryCount;
        this.totalMatchCount = totalMatchCount;
    }

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public void setMovieTitle(String movieTitle) {
        this.movieTitle = movieTitle;
    }

    public int getMatchingActorCount() {
        return matchingActorCount;
    }

    public void setMatchingActorCount(int matchingActorCount) {
        this.matchingActorCount = matchingActorCount;
    }

    public int getMatchingCategoryCount() {
        return matchingCategoryCount;
    }

    public void setMatchingCategoryCount(int matchingCategoryCount) {
        this.matchingCategoryCount = matchingCategoryCount;
    }

    public int getTotalMatchCount() {
        return totalMatchCount;
    }

    public void setTotalMatchCount(int totalMatchCount) {
        this.totalMatchCount = totalMatchCount;
    }

    @Override
    public String toString() {
        return "SimilarMovieData{" +
                "movieId=" + movieId +
                ", movieTitle='" + movieTitle + '\'' +
                ", matchingActorCount=" + matchingActorCount +
                ", matchingCategoryCount=" + matchingCategoryCount +
                ", totalMatchCount=" + totalMatchCount +
                '}';
    }
}
