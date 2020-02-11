package com.akshay.tigga.mymoviedb.controller;

import com.akshay.tigga.mymoviedb.business.FetchMovieService;
import com.akshay.tigga.mymoviedb.data.model.Movie;
import com.akshay.tigga.mymoviedb.data.model.SimilarMovieData;
import com.akshay.tigga.mymoviedb.exception.InvalidParameterException;
import com.akshay.tigga.mymoviedb.exception.MovieNotFoundException;
import com.akshay.tigga.mymoviedb.util.Utility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class MoviesController {
    @Autowired
    FetchMovieService fetchMovieService;

    @GetMapping(path = "/")
    public String getApiDetails() {
        return Utility.getApiInfo();
    }

    @GetMapping(path = "/movies", produces = {"application/json", "application/xml"})
    public @ResponseBody
    List<String> getAllMovies(@RequestParam(required = false, defaultValue = "title.asc") String sort) {
        if (!Utility.isInputSortValid(sort)) {
            throw new InvalidParameterException("Invalid value \'" + sort + "\' for \'sort\'");
        }
        return fetchMovieService.getAllMovieTitles(sort);
    }

    @GetMapping(path = "/movies/{title}", produces = {"application/json", "application/xml"})
    public @ResponseBody
    Movie getMovieForTitle(@PathVariable String title) {
        Movie movie = fetchMovieService.getMovieForTitle(title);
        if (movie == null) {
            throw new MovieNotFoundException("No movie with title = " + title + " in our database.");
        }
        return movie;
    }

    @GetMapping(path = "/similarmovies", produces = {"application/json", "application/xml"})
    public @ResponseBody
    List<SimilarMovieData> getSimilarMovies(@RequestParam(required = true) String title,
                                            @RequestParam(required = false, defaultValue = "0") String limit) {
        int resLimit = 0;
        try {
            resLimit = Integer.parseInt(limit);
        } catch (NumberFormatException ex) {
            throw new InvalidParameterException("\'" + limit + "\' is not a valid number");
        }

        if (resLimit < 0) {
            throw new InvalidParameterException("\'limit\' cannot be negative.");
        }

        List<SimilarMovieData> similarMovies = fetchMovieService.getSimilarMovies(title, resLimit);
        if (similarMovies == null) {
            throw new MovieNotFoundException("No movie with title = " + title + " in our database.");
        } else if (similarMovies.isEmpty()) {
            throw new MovieNotFoundException("There are no movies similar to " + title + " in our database.");
        }
        return similarMovies;
    }
}
