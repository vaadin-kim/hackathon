package org.vaadin.hackme.spring.views.news;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class NewsMapper implements RowMapper<NewsModel> {

	@Override
	public NewsModel mapRow(ResultSet rs, int rowNum) throws SQLException {
		NewsModel model = new NewsModel();
		model.setId(rs.getInt("id"));
		model.setArticle(rs.getString("article"));
		return model;
	}

}
