package org.vaadin.hackme.spring.views.news;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.hackme.spring.MainView;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;

@Route(value = "news", layout = MainView.class)
@RouteAlias(value = "", layout = MainView.class)
@PageTitle("News")
public class NewsView  extends Div {

	@Autowired
	private NewsRepository newsRepository;

	@PostConstruct
	private void setUp() {
		List<NewsModel> newsArticles = newsRepository.getNews();
		for (NewsModel newsArticle : newsArticles) {
			Div news = new Div();
			news.getElement().setProperty("innerHTML", newsArticle.getArticle());
			add(news);
		}

	}

}
