package com.akshay.tigga.mymoviedb;

import com.akshay.tigga.mymoviedb.business.GenerateMovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {
    @Autowired
    private GenerateMovieService generateMovieService;

    @Override
    public void run(String... args) throws Exception {
        generateMovieService.populateDb();
    }
}