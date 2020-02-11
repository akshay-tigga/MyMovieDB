package com.akshay.tigga.mymoviedb.data.model;

public class BasicMovieData {
    private String title;
    private String releaseDate;
    private String duration;
    private String budget;
    private String collection;
    private String summary;
    private int categoryCount;

    public BasicMovieData() {
    }

    public BasicMovieData(String title, String releaseDate, String duration, String budget, String collection,
                          String summary, int categoryCount) {
        this.title = title;
        this.releaseDate = releaseDate;
        this.duration = duration;
        this.budget = budget;
        this.collection = collection;
        this.summary = summary;
        this.categoryCount = categoryCount;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getBudget() {
        return budget;
    }

    public void setBudget(String budget) {
        this.budget = budget;
    }

    public String getCollection() {
        return collection;
    }

    public void setCollection(String collection) {
        this.collection = collection;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public int getCategoryCount() {
        return categoryCount;
    }

    public void setCategoryCount(int categoryCount) {
        this.categoryCount = categoryCount;
    }

    @Override
    public String toString() {
        return "BasicMovieData{" +
                "title='" + title + '\'' +
                ", releaseDate='" + releaseDate + '\'' +
                ", duration='" + duration + '\'' +
                ", budget='" + budget + '\'' +
                ", collection='" + collection + '\'' +
                ", summary='" + summary + '\'' +
                ", categoryCount=" + categoryCount +
                '}';
    }
}
