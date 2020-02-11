package com.akshay.tigga.mymoviedb.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Component
public class Utility {
    @Autowired
    private Environment env;

    private static Logger logger = LoggerFactory.getLogger(Utility.class);

    private static final String MOVIE_LIST = "Free Solo, Solo: A Star Wars Story, Super Troopers 2, Can You Ever Forgive Me?, mid90s, Andhadhun, Hell Fest, Hotel Artemis, Please Stand By, The Night Comes for Us, The Spy Who Dumped Me, Sicario: Day of the Soldado, Support the Girls, Alad'2, Dark Crimes, Slaughterhouse Rulez, Braven, Fantastic Beasts: The Crimes of Grindelwald, Mia and the White Lion, Mamma Mia! Here We Go Again, Godzilla: City on the Edge of Battle, Uncle Drew, Red Joan, Sherlock Gnomes, Destination Wedding, Anna and the Apocalypse, Candy Jar, On the Basis of Sex, Flavors of Youth, The Holiday Calendar, Elizabeth Harvest, Ben Is Back, The Equalizer 2, Day of the Dead: Bloodline, The Miracle Season, The Ballad of Buster Scruggs, Suspiria, BlacKkKlansman, Ant-Man and the Wasp, Like Father, Your Son, The Night Eats the World, Jurassic World: Fallen Kingdom, Hellraiser: Judgment, They'll Love Me When I'm Dead, Operation Finale, The 15:17 to Paris, Insidious: The Last Key, Mowgli: Legend of the Jungle, Sorry to Bother You, Incredibles 2, Batman: Gotham by Gaslight, Hold the Dark, Vox Lux, The Hurricane Heist, First Reformed, A Private War, Hotel Transylvania 3: Summer Vacation, Illang: The Wolf Brigade, The Nutcracker and the Four Realms, The Leisure Seeker, Bad Times at the El Royale, Teen Titans Go! To the Movies, 2036 Origin Unknown, Goosebumps 2: Haunted Halloween, The Cured, Rolling to You, The First Purge, Unfriended: Dark Web, Set It Up, Mary Poppins Returns, Where Hands Touch, Deadpool 2, The Scorpion King: Book of Souls, 12 Strong, The Cloverfield Paradox, Mile 22, Three Identical Strangers, Lean on Pete, Show Dogs, Alex Strangelove, Acts of Violence, Johnny English Strikes Again, Forever My Girl, Action Point, Spider-Man: Into the Spider-Verse, The Perfection, Boy Erased, Red Sparrow, Blindspotting, Redcon-1, The Con Is On, A.I. Rising, The Tale, Summer of 84, Love Jacked, Welcome to Marwen, Behind the Curve, Woman Walks Ahead, Escape Plan 2: Hades, The Darkest Minds, The Favourite, Duck Butter, First Match, Hagazussa, Operation Red Sea, Pacific Rim: Uprising, Tremors: A Cold Day in Hell, Creed II, Instant Family, Ghostland, The Meg, Sierra Burgess Is a Loser, When We First Met, Avengers: Infinity War, White Boy Rick, American Animals, Assassination Nation, Under the Silver Lake, Hearts Beat Loud, Black Mirror: Bandersnatch, Shoplifters, Swiped, The Week Of, Family Blood, Maquia: When the Promised Flower Blooms, A Christmas Prince: The Royal Wedding, Return of the Hero, Unsane, The Strangers: Prey at Night, The Christmas Chronicles, Batman Ninja, Ralph Breaks the Internet, Outlaw King, The Thinning: New World Order, The Happytime Murders";

    private static final String FETCH_SIMILAR_MOVIE_WITH_LIMIT = "select tableTwo.m_id, tableTwo.count_a, tableTwo.count_c, tableTwo.count_a+tableTwo.count_c as total from " +
            "(select tableOne.m_id, sum(tableOne.count_a) as count_a, sum(tableOne.count_c) as count_c from " +
            "(select m_id, count(m_id) as count_a, 0 as count_c from " +
            "movie_actor_res where a_id in " +
            "(select a_id from movie_actor_res where m_id = %d) " +
            "and m_id <> %d " +
            "group by m_id " +
            "union all " +
            "select m_id, 0 as count_a, count(m_id) as count_c from " +
            "movie_category_res where c_id in " +
            "(select c_id from movie_category_res where m_id = %d) " +
            "and m_id <> %d " +
            "group by m_id) as tableOne " +
            "group by tableOne.m_id) as tableTwo " +
            "order by total desc " +
            "limit %d;";

    private static final String FETCH_SIMILAR_MOVIE_WITHOUT_LIMIT = "select tableTwo.m_id, tableTwo.count_a, tableTwo.count_c, tableTwo.count_a+tableTwo.count_c as total from " +
            "(select tableOne.m_id, sum(tableOne.count_a) as count_a, sum(tableOne.count_c) as count_c from " +
            "(select m_id, count(m_id) as count_a, 0 as count_c from " +
            "movie_actor_res where a_id in " +
            "(select a_id from movie_actor_res where m_id = %d) " +
            "and m_id <> %d " +
            "group by m_id " +
            "union all " +
            "select m_id, 0 as count_a, count(m_id) as count_c from " +
            "movie_category_res where c_id in " +
            "(select c_id from movie_category_res where m_id = %d) " +
            "and m_id <> %d " +
            "group by m_id) as tableOne " +
            "group by tableOne.m_id) as tableTwo " +
            "order by total desc;";


    public static String getApiInfo() {
        return ">> API 1 <<\n" +
                "Return sorted list of movie titles.\n" +
                "Url : /movies\n" +
                "Additional parameters : 'format' and 'sort'\n" +
                "'format' - defines return format, json or xml. Default is json. (Example: format=xml)\n" +
                "'sort' - defines the sorting method used. Default is 'title.asc'. (Example: title.desc)\n" +
                "       - Valid values are 'title.asc', 'title.desc', 'date.asc', 'date.desc', 'category.asc', 'category.desc'\n" +
                "\n" +
                "Examples of API - \n" +
                "/movies?format=xml&sort=date.desc  : Return movie titles sorted by release date in descending order\n" +
                "/movies?format=json&sort=category.asc :  Return movie titles sorted by category count(total number of category tags for the movie) in ascending order\n" +
                " \n" +
                "---\n" +
                "\n" +
                ">> API 2 <<\n" +
                "Return all the information about a given movie\n" +
                "Url : /movies/{title}\n" +
                "    : Where {title} is the name of the movie you want information for\n" +
                "Additional parameters : 'format'\n" +
                "'format' - defines return format, json or xml. Default is json. (Example: format=xml)\n" +
                "\n" +
                "Examples of API - \n" +
                "/movies/Free%20Solo  : Return all information for the movie 'Free Solo'\n" +
                "/movies/Free%20Solo?format=xml  : Return all information for the movie 'Free Solo' in xml format\n" +
                "\n" +
                "---\n" +
                "\n" +
                ">> API 3 <<\n" +
                "Return a list of similar movie titles ordered by the most similar movie first.\n" +
                "Url : /similarmovies?title={movie_name}\n" +
                "    : Where {movie_name} is the name of the movie for which you want to find similar movies\n" +
                "Additional parameters : 'format' and 'limit'\n" +
                "'format' - defines return format, json or xml. Default is json. (Example: format=xml)\n" +
                "'limit' - Use this if you want to limit the number of movie titles you get back as result. Default value is '0'. A value of '0' will return all the similar movie titles.\n" +
                "\n" +
                "Examples of API -\n" +
                "/similarmovies?title=Free%20Solo&limit=5  : Return top 5 movies similar to 'Free Solo'\n" +
                "similarmovies?title=Free%20Solo&format=xml  : Return all the movies similar to 'Free Solo' in xml format.";
    }

    public static String getQueryForMovieTitles(String sort) {
        switch (sort) {
            case "title.desc":
                return "select title from movies order by title desc";
            case "date.asc":
                return "select title from movies order by release_date asc";
            case "date.desc":
                return "select title from movies order by release_date desc";
            case "category.asc":
                return "select title from movies order by category_count asc";
            case "category.desc":
                return "select title from movies order by category_count desc";
            case "title.asc":
            default:
                return "select title from movies order by title asc";
        }
    }

    public static boolean isInputSortValid(String sort) {
        switch (sort) {
            case "title.asc":
            case "title.desc":
            case "date.asc":
            case "date.desc":
            case "category.asc":
            case "category.desc":
                return true;
            default:
                return false;
        }
    }

    public static String getQueryForSimilarMovies(int movieId, int limit) {
        if (limit == 0) {
            return String.format(FETCH_SIMILAR_MOVIE_WITHOUT_LIMIT, movieId, movieId, movieId, movieId);
        } else if (limit > 0){
            return String.format(FETCH_SIMILAR_MOVIE_WITH_LIMIT, movieId, movieId, movieId, movieId, limit);
        } else {
            // Todo throw exception here
            return null; // limit cannot be negative
        }
    }

    public static boolean isValidDate(String date) {
        if (date == null || date.trim().isEmpty()) {
            return false;
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            simpleDateFormat.parse(date);
        } catch (ParseException ex) {
            return false;
        }
        return true;
    }

    public List<String> getStoredMovieList() {
        int moviesToFetch = 100;
        try {
            moviesToFetch = Integer.parseInt(Objects.requireNonNull(env.getProperty("movies.to.fetch")));
        } catch (NullPointerException ex) {
            logger.error("Failed to get value of \'movies.to.fetch\' from application properties. " +
                    "Using default value(100). Exception: " + ex);
            moviesToFetch = 100;  // defensive coding
        }
        logger.info("Movies to fetch = " + moviesToFetch);
        List<String> movieList = new ArrayList<>();
        movieList.addAll(Arrays.asList(MOVIE_LIST.split(", ")).subList(0, moviesToFetch));
        return movieList;
    }
}
