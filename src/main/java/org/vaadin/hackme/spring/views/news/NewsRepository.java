package org.vaadin.hackme.spring.views.news;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class NewsRepository {

	@Autowired
	JdbcTemplate jdbcTemplate;

	public List<NewsModel> getNews() {
		List<NewsModel> news = this.jdbcTemplate.query("select id, article from news order by id", new NewsMapper());
		return news;
	}

}
