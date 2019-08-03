package org.vaadin.hackme.spring.views.news;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.hackme.spring.MainView;
import org.vaadin.hackme.spring.views.usermanagement.UserManagementView.UserManagementViewModel;

import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.polymertemplate.Id;
import com.vaadin.flow.component.polymertemplate.PolymerTemplate;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;

@Route(value = "news", layout = MainView.class)
@RouteAlias(value = "", layout = MainView.class)
@PageTitle("News")
@JsModule("src/views/news/news-view.js")
@Tag("news-view")
public class NewsView extends PolymerTemplate<UserManagementViewModel> {
	
	@Id
	private VerticalLayout layout;

	@Autowired
	private NewsRepository newsRepository;

	@PostConstruct
	private void setUp() {
		List<NewsModel> newsArticles = newsRepository.getNews();
		for (NewsModel newsArticle : newsArticles) {
			Div news = new Div();
			news.setClassName("bulletin");
			news.getElement().setProperty("innerHTML", newsArticle.getArticle());
			layout.add(news);
		}
	}

}
