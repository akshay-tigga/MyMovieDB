package com.akshay.tigga.mymoviedb.data.model;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SimilarMovieDataRowMapper implements RowMapper<SimilarMovieData> {
    @Override
    public SimilarMovieData mapRow(ResultSet rs, int rowNum) throws SQLException {
        SimilarMovieData similarMovieData = new SimilarMovieData();
        similarMovieData.setMovieId(rs.getInt("m_id"));
        similarMovieData.setMatchingActorCount(rs.getInt("count_a"));
        similarMovieData.setMatchingCategoryCount(rs.getInt("count_c"));
        similarMovieData.setTotalMatchCount(rs.getInt("total"));
        return similarMovieData;
    }
}
