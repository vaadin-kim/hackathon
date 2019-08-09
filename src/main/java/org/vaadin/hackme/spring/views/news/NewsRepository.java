package org.vaadin.hackme.spring.views.news;

import java.sql.PreparedStatement;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

@Component
public class NewsRepository {

	@Autowired
	JdbcTemplate jdbcTemplate;

	public List<NewsModel> getNews() {
		List<NewsModel> news = this.jdbcTemplate.query("select id, article from news order by id", new NewsMapper());
		return news;
	}

	public void store(NewsModel news) {
		if (news == null)
			return;

		if (news.getId() != null) {
			update(news);
		} else {
			insert(news);
		}

	}

	private void insert(NewsModel news) {
		String query = "insert into news (article) values (?)";
		this.jdbcTemplate.update(query, new Object[] { news.getArticle()});
	}

	private void update(NewsModel news) {
		String query = "update news set article = ? where id = ?";
		this.jdbcTemplate.update(query, new Object[] { news.getArticle(), news.getId() });
	}

}
