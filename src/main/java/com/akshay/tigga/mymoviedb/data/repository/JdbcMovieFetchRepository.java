package com.akshay.tigga.mymoviedb.data.repository;

import com.akshay.tigga.mymoviedb.data.model.BasicMovieData;
import com.akshay.tigga.mymoviedb.data.model.BasicMovieDataRowMapper;
import com.akshay.tigga.mymoviedb.data.model.SimilarMovieData;
import com.akshay.tigga.mymoviedb.data.model.SimilarMovieDataRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class JdbcMovieFetchRepository implements MovieFetchRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Integer getMovieId(String movieTitle) {
        try {
            return jdbcTemplate.queryForObject("select m_id from movies where title=?", new Object[]{movieTitle}, Integer.class);
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    public String getMovieTitle(int movieId) {
        try {
            return jdbcTemplate.queryForObject("select title from movies where m_id=?", new Object[]{movieId}, String.class);
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    public Integer getActorId(String actor) {
        try {
            return jdbcTemplate.queryForObject("select a_id from actors where a_name=?", new Object[]{actor}, Integer.class);
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    public Integer getProducerId(String producer) {
        try {
            return jdbcTemplate.queryForObject("select p_id from producers where p_name=?", new Object[]{producer}, Integer.class);
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    public Integer getProductionHouseId(String productionHouse) {
        try {
            return jdbcTemplate.queryForObject("select ph_id from production_houses where ph_name=?", new Object[]{productionHouse}, Integer.class);
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    public Integer getDirectorId(String director) {
        try {
            return jdbcTemplate.queryForObject("select d_id from directors where d_name=?", new Object[]{director}, Integer.class);
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    public Integer getMusicComposerId(String musicComposer) {
        try {
            return jdbcTemplate.queryForObject("select mc_id from music_composers where mc_name=?", new Object[]{musicComposer}, Integer.class);
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    public Integer getLanguageId(String language) {
        try {
            return jdbcTemplate.queryForObject("select l_id from languages where l_name=?", new Object[]{language}, Integer.class);
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    public Integer getCategoryId(String category) {
        try {
            return jdbcTemplate.queryForObject("select c_id from categories where c_name=?", new Object[]{category}, Integer.class);
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    public List<String> getAllMovieTitles(String sqlQuery) {
        return jdbcTemplate.queryForList(sqlQuery, String.class);
    }

    public List<String> getActorsForMovieTitle(String title) {
        return jdbcTemplate.queryForList("select a_name from actors where a_id in " +
                "(select a_id from movie_actor_res where m_id in " +
                "(select m_id from movies where title=?));", new Object[]{title}, String.class);
    }

    public List<String> getProducersForMovieTitle(String title) {
        return jdbcTemplate.queryForList("select p_name from producers where p_id in " +
                "(select p_id from movie_producer_res where m_id in " +
                "(select m_id from movies where title=?));", new Object[]{title}, String.class);
    }

    public List<String> getProdHousesForMovieTitle(String title) {
        return jdbcTemplate.queryForList("select ph_name from production_houses where ph_id in " +
                "(select ph_id from movie_prod_house_res where m_id in " +
                "(select m_id from movies where title=?));", new Object[]{title}, String.class);
    }

    public List<String> getDirectorsForMovieTitle(String title) {
        return jdbcTemplate.queryForList("select d_name from directors where d_id in " +
                "(select d_id from movie_director_res where m_id in " +
                "(select m_id from movies where title=?));", new Object[]{title}, String.class);
    }

    public List<String> getMusicCompsForMovieTitle(String title) {
        return jdbcTemplate.queryForList("select mc_name from music_composers where mc_id in " +
                "(select mc_id from movie_music_comp_res where m_id in " +
                "(select m_id from movies where title=?));", new Object[]{title}, String.class);
    }

    public List<String> getLanguagesForMovieTitle(String title) {
        return jdbcTemplate.queryForList("select l_name from languages where l_id in " +
                "(select l_id from movie_language_res where m_id in " +
                "(select m_id from movies where title=?));", new Object[]{title}, String.class);
    }

    public List<String> getCategoriesForMovieTitle(String title) {
        return jdbcTemplate.queryForList("select c_name from categories where c_id in " +
                "(select c_id from movie_category_res where m_id in " +
                "(select m_id from movies where title=?));", new Object[]{title}, String.class);
    }

    public BasicMovieData getBasicMovieDataForTitle(String title) {
        try {
            return jdbcTemplate.queryForObject("select * from movies where title=?", new Object[]{title},
                    new BasicMovieDataRowMapper());
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    public List<SimilarMovieData> getSimilarMovies(String query) {
        try {
            List<SimilarMovieData> movieList = jdbcTemplate.query(query, new SimilarMovieDataRowMapper());
            for (SimilarMovieData movie : movieList) {
                movie.setMovieTitle(getMovieTitle(movie.getMovieId()));
            }
            return movieList;
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

}
