package com.akshay.tigga.mymoviedb.data.model;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BasicMovieDataRowMapper implements RowMapper<BasicMovieData> {
    @Override
    public BasicMovieData mapRow(ResultSet rs, int rowNum) throws SQLException {
        BasicMovieData basicMovieData = new BasicMovieData();
        basicMovieData.setTitle(rs.getString("title"));
        basicMovieData.setReleaseDate(rs.getDate("release_date").toString());
        basicMovieData.setDuration(rs.getString("duration"));
        basicMovieData.setBudget(rs.getString("budget"));
        basicMovieData.setCollection(rs.getString("collection"));
        basicMovieData.setSummary(rs.getString("summary"));
        basicMovieData.setCategoryCount(rs.getInt("category_count"));
        return basicMovieData;
    }
}
