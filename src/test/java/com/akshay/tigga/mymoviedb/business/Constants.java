package com.akshay.tigga.mymoviedb.business;

import java.util.Arrays;
import java.util.List;

public class Constants {
    // List of movie titles
    protected static final String MOVIE_LIST_TITLE_ASC = "Alad'2, Andhadhun, Braven, Can You Ever Forgive Me?, Dark Crimes," +
            " Fantastic Beasts: The Crimes of Grindelwald, Free Solo, Hell Fest, Hotel Artemis, Mamma Mia! Here We Go Again, Mia and the White Lion, Please Stand By, Sicario: Day of the Soldado, Slaughterhouse Rulez, Solo: A Star Wars Story, Super Troopers 2, Support the Girls, The Night Comes for Us, The Spy Who Dumped Me, mid90s";
    protected static final String MOVIE_LIST_DATE_DESC = "Mia and the White Lion, Fantastic Beasts: The Crimes of Grindelwald, " +
            "Alad'2, Slaughterhouse Rulez, Andhadhun, Hell Fest, The Night Comes for Us, mid90s, Can You Ever Forgive Me?, Free Solo, The Spy Who Dumped Me, Mamma Mia! Here We Go Again, Sicario: Day of the Soldado, Hotel Artemis, Solo: A Star Wars Story, Super Troopers 2, Support the Girls, Braven, Please Stand By, Dark Crimes";

    // Movie Details for 'Free Solo'
    protected static final String MOVIE_TITLE = "Free Solo";
    protected static final String MOVIE_REL_DATE = "2018-08-31";
    protected static final String MOVIE_DURATION = "100 minutes";
    protected static final String MOVIE_BUDGET = "Data not available";
    protected static final String MOVIE_COLLECTION = "$28.6 million";
    protected static final String MOVIE_SUMMARY = "Free Solo is a 2018 American documentary film directed by Elizabeth Chai Vasarhelyi and Jimmy Chin that profiles rock climber Alex Honnold on his quest to perform a free solo climb of El Capitan in June 2017.";
    protected static final int MOVIE_CATEGORY_COUNT = 10;

    protected static final List<String> MOVIE_ACTORS = Arrays.asList("Alex Honnold", "Sanni McCandless", "Jimmy Chin", "Tommy Caldwell");
    protected static final List<String> MOVIE_PRODUCERS = Arrays.asList("Elizabeth Chai Vasarhelyi", "Jimmy Chin", "Shannon Dill", "Evan Hayes");
    protected static final List<String> MOVIE_PROD_HOUSES = Arrays.asList("Little Monster Films", "Itinerant Media", "Parkes+MacDonald/Image Nation", "National Geographic Documentary Films");
    protected static final List<String> MOVIE_DIRECTORS = Arrays.asList("Elizabeth Chai Vasarhelyi", "Jimmy Chin");
    protected static final List<String> MOVIE_MUSIC_COMP = Arrays.asList("Marco Beltrami");
    protected static final List<String> MOVIE_LANG = Arrays.asList("English");
    protected static final List<String> MOVIE_CATEGORY = Arrays.asList("2018 films", "American films", "American documentary films", "American sports films", "Films scored by Marco Beltrami", "National Geographic Society films", "Documentary films about climbing", "Best Documentary Feature Academy Award winners", "Yosemite National Park", "Films set in California");

    // Top 2 similar movies for 'Free Solo'
    protected static final String FS_TITLE_1 = "Support the Girls";
    protected static final int FS_ACTORS_1 = 0;
    protected static final int FS_CATEGORY_1 = 2;
    protected static final int FS_TOTAL_1 = 2;

    protected static final String FS_TITLE_2 = "Hotel Artemis";
    protected static final int FS_ACTORS_2 = 0;
    protected static final int FS_CATEGORY_2 = 2;
    protected static final int FS_TOTAL_2 = 2;

}
