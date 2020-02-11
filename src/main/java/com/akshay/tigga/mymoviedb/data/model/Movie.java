package com.akshay.tigga.mymoviedb.data.model;

import java.util.List;

public class Movie {
    private BasicMovieData basicMovieData;

    private List<String> actors;
    private List<String> producers;
    private List<String> productionHouses;
    private List<String> directors;
    private List<String> musicComposers;
    private List<String> languages;
    private List<String> categories;

    public Movie() {
    }

    public Movie(BasicMovieData basicMovieData, List<String> actors, List<String> producers, List<String> productionHouses,
                 List<String> directors, List<String> musicComposers, List<String> languages, List<String> categories) {
        this.basicMovieData = basicMovieData;
        this.actors = actors;
        this.producers = producers;
        this.productionHouses = productionHouses;
        this.directors = directors;
        this.musicComposers = musicComposers;
        this.languages = languages;
        this.categories = categories;
    }

    public BasicMovieData getBasicMovieData() {
        return basicMovieData;
    }

    public void setBasicMovieData(BasicMovieData basicMovieData) {
        this.basicMovieData = basicMovieData;
    }

    public List<String> getActors() {
        return actors;
    }

    public void setActors(List<String> actors) {
        this.actors = actors;
    }

    public List<String> getProducers() {
        return producers;
    }

    public void setProducers(List<String> producers) {
        this.producers = producers;
    }

    public List<String> getProductionHouses() {
        return productionHouses;
    }

    public void setProductionHouses(List<String> productionHouses) {
        this.productionHouses = productionHouses;
    }

    public List<String> getDirectors() {
        return directors;
    }

    public void setDirectors(List<String> directors) {
        this.directors = directors;
    }

    public List<String> getMusicComposers() {
        return musicComposers;
    }

    public void setMusicComposers(List<String> musicComposers) {
        this.musicComposers = musicComposers;
    }

    public List<String> getLanguages() {
        return languages;
    }

    public void setLanguages(List<String> languages) {
        this.languages = languages;
    }

    public List<String> getCategories() {
        return categories;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "basicMovieData=" + basicMovieData +
                ", actors=" + actors +
                ", producers=" + producers +
                ", productionHouses=" + productionHouses +
                ", directors=" + directors +
                ", musicComposers=" + musicComposers +
                ", languages=" + languages +
                ", categories=" + categories +
                '}';
    }
}
