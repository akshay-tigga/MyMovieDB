package com.akshay.tigga.mymoviedb.data.repository;

import com.akshay.tigga.mymoviedb.data.model.BasicMovieData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcMovieInsertRepository implements MovieInsertRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private MovieFetchRepository movieFetchRepository;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public Integer insertBasicMovieData(BasicMovieData basicMovieData) {
        try {
            jdbcTemplate.update("insert into movies(title, release_date, duration, budget, collection, summary, category_count) " +
                    "values ( ?, ?, ?, ?, ?, ?, ?)", new Object[]{
                    basicMovieData.getTitle(), basicMovieData.getReleaseDate(), basicMovieData.getDuration(),
                    basicMovieData.getBudget(), basicMovieData.getCollection(), basicMovieData.getSummary(), basicMovieData.getCategoryCount()
            });
        } catch (DataIntegrityViolationException ex) {
            logger.error("Insert failed for " + basicMovieData.toString() + " ex = " + ex);
            //todo: handle null is receiving method
            return null;
        }
        return movieFetchRepository.getMovieId(basicMovieData.getTitle());
    }

    public Integer insertActorDetails(String actorName) {
        try {
            jdbcTemplate.update("insert into actors(a_name) values(?)", new Object[]{actorName});
        } catch (DataIntegrityViolationException ex) {
            logger.error("Insert failed for " + actorName + " ex = " + ex);
            //todo: handle null is receiving method
            return null;
        }
        return movieFetchRepository.getActorId(actorName);
    }

    public Integer insertIntoMovieActorResTable(int mId, int aId) {
        try {
            jdbcTemplate.update("insert into movie_actor_res(m_id, a_id) values(?, ?)", new Object[]{mId, aId});
        } catch (DataIntegrityViolationException ex) {
            logger.error("Insert failed for mId:" + mId + " aId:" + aId + " ex = " + ex);
            //todo: handle null is receiving method
            return null;
        }
        return 1; // 1 -> success
    }

    public Integer insertProducerDetails(String producerName) {
        try {
            jdbcTemplate.update("insert into producers(p_name) values(?)", new Object[]{producerName});
        } catch (DataIntegrityViolationException ex) {
            logger.error("Insert failed for " + producerName + " ex = " + ex);
            //todo: handle null is receiving method
            return null;
        }
        return movieFetchRepository.getProducerId(producerName);
    }

    public Integer insertIntoMovieProducerResTable(int mId, int pId) {
        try {
            jdbcTemplate.update("insert into movie_producer_res(m_id, p_id) values(?, ?)", new Object[]{mId, pId});
        } catch (DataIntegrityViolationException ex) {
            logger.error("Insert failed for mId:" + mId + " pId:" + pId + " ex = " + ex);
            //todo: handle null is receiving method
            return null;
        }
        return 1; // 1 -> success
    }

    public Integer insertProductionHouseDetails(String productionHouseName) {
        try {
            jdbcTemplate.update("insert into production_houses(ph_name) values(?)", new Object[]{productionHouseName});
        } catch (DataIntegrityViolationException ex) {
            logger.error("Insert failed for " + productionHouseName + " ex = " + ex);
            //todo: handle null is receiving method
            return null;
        }
        return movieFetchRepository.getProductionHouseId(productionHouseName);
    }

    public Integer insertIntoMovieProductionHouseResTable(int mId, int phId) {
        try {
            jdbcTemplate.update("insert into movie_prod_house_res(m_id, ph_id) values(?, ?)", new Object[]{mId, phId});
        } catch (DataIntegrityViolationException ex) {
            logger.error("Insert failed for mId:" + mId + " phId:" + phId + " ex = " + ex);
            //todo: handle null is receiving method
            return null;
        }
        return 1; // 1 -> success
    }

    public Integer insertDirectorDetails(String directorName) {
        try {
            jdbcTemplate.update("insert into directors(d_name) values(?)", new Object[]{directorName});
        } catch (DataIntegrityViolationException ex) {
            logger.error("Insert failed for " + directorName + " ex = " + ex);
            //todo: handle null is receiving method
            return null;
        }
        return movieFetchRepository.getDirectorId(directorName);
    }

    public Integer insertIntoMovieDirectorResTable(int mId, int dId) {
        try {
            jdbcTemplate.update("insert into movie_director_res(m_id, d_id) values(?, ?)", new Object[]{mId, dId});
        } catch (DataIntegrityViolationException ex) {
            logger.error("Insert failed for mId:" + mId + " dId:" + dId + " ex = " + ex);
            //todo: handle null is receiving method
            return null;
        }
        return 1; // 1 -> success
    }

    public Integer insertMusicComposerDetails(String musicComposerName) {
        try {
            jdbcTemplate.update("insert into music_composers(mc_name) values(?)", new Object[]{musicComposerName});
        } catch (DataIntegrityViolationException ex) {
            logger.error("Insert failed for " + musicComposerName + " ex = " + ex);
            //todo: handle null is receiving method
            return null;
        }
        return movieFetchRepository.getMusicComposerId(musicComposerName);
    }

    public Integer insertIntoMovieMusicComposerResTable(int mId, int mcId) {
        try {
            jdbcTemplate.update("insert into movie_music_comp_res(m_id, mc_id) values(?, ?)", new Object[]{mId, mcId});
        } catch (DataIntegrityViolationException ex) {
            logger.error("Insert failed for mId:" + mId + " mcId:" + mcId + " ex = " + ex);
            //todo: handle null is receiving method
            return null;
        }
        return 1; // 1 -> success
    }

    public Integer insertLanguageDetails(String language) {
        try {
            jdbcTemplate.update("insert into languages(l_name) values(?)", new Object[]{language});
        } catch (DataIntegrityViolationException ex) {
            logger.error("Insert failed for " + language + " ex = " + ex);
            //todo: handle null is receiving method
            return null;
        }
        return movieFetchRepository.getLanguageId(language);
    }

    public Integer insertIntoMovieLanguageResTable(int mId, int lId) {
        try {
            jdbcTemplate.update("insert into movie_language_res(m_id, l_id) values(?, ?)", new Object[]{mId, lId});
        } catch (DataIntegrityViolationException ex) {
            logger.error("Insert failed for mId:" + mId + " lId:" + lId + " ex = " + ex);
            //todo: handle null is receiving method
            return null;
        }
        return 1; // 1 -> success
    }

    public Integer insertCategoryDetails(String category) {
        try {
            jdbcTemplate.update("insert into categories(c_name) values(?)", new Object[]{category});
        } catch (DataIntegrityViolationException ex) {
            logger.error("Insert failed for " + category + " ex = " + ex);
            //todo: handle null is receiving method
            return null;
        }
        return movieFetchRepository.getCategoryId(category);
    }

    public Integer insertIntoMovieCategoryResTable(int mId, int cId) {
        try {
            jdbcTemplate.update("insert into movie_category_res(m_id, c_id) values(?, ?)", new Object[]{mId, cId});
        } catch (DataIntegrityViolationException ex) {
            logger.error("Insert failed for mId:" + mId + " cId:" + cId + " ex = " + ex);
            //todo: handle null is receiving method
            return null;
        }
        return 1; // 1 -> success
    }

}
