package org.vaadin.hackme.spring.views.publishing;

import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.hackme.spring.MainView;
import org.vaadin.hackme.spring.users.ActiveUser;
import org.vaadin.hackme.spring.users.Role;
import org.vaadin.hackme.spring.views.news.NewsModel;
import org.vaadin.hackme.spring.views.news.NewsRepository;
import org.vaadin.hackme.spring.views.publishing.PublishingView.PublishingViewModel;

import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.polymertemplate.Id;
import com.vaadin.flow.component.polymertemplate.PolymerTemplate;
import com.vaadin.flow.component.richtexteditor.RichTextEditor;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.router.AfterNavigationEvent;
import com.vaadin.flow.router.AfterNavigationObserver;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.templatemodel.TemplateModel;

@SuppressWarnings("serial")
@Route(value = "publishing", layout = MainView.class)
@PageTitle("Publishing")
@JsModule("src/views/publishing/publishing-view.js")
@Tag("publishing-view")
public class PublishingView extends PolymerTemplate<PublishingViewModel>
		implements AfterNavigationObserver, BeforeEnterObserver {

	public static interface PublishingViewModel extends TemplateModel {
	}

	@Autowired
	private NewsRepository service;

	@Autowired
	private ActiveUser activeUser;

	@Id
	private Grid<NewsModel> news;

	@Id
	private TextArea article;

	@Id
	private Button cancel;
	@Id
	private Button save;

	@Id("create-new")
	private Button createNew;

	private Binder<NewsModel> binder;

	private NewsModel editItem;

	public PublishingView() {
		// Configure Grid
		news.addColumn(NewsModel::getArticle).setHeader("Content");

		// when a row is selected or deselected, populate form
		news.asSingleSelect().addValueChangeListener(event -> populateForm(event.getValue()));

		// Configure Form
		binder = new Binder<>(NewsModel.class);

		binder.bind(article, "article");
		// the grid valueChangeEvent will clear the form too
		cancel.addClickListener(e -> news.asSingleSelect().clear());

		save.addClickListener(e -> {
			try {
				binder.writeBean(editItem);
			} catch (ValidationException e1) {
				e1.printStackTrace();
			}
			if(editItem.getId() == null) {
				service.store(editItem);
				news.setItems(service.getNews());
				populateForm(null);
			} else {
				service.store(editItem);
				news.asSingleSelect().clear();
			}
		});

		createNew.addClickListener(e -> {
			NewsModel model = new NewsModel();
			populateForm(model);
		});
	}

	@Override
	public void afterNavigation(AfterNavigationEvent event) {
		// Lazy init of the grid items, happens only when we are sure the view will be
		// shown to the user
		news.setItems(service.getNews());

		article.setEnabled(false);
		save.setEnabled(false);
		cancel.setEnabled(false);
	}

	private void populateForm(NewsModel value) {
		editItem = value;
		article.setEnabled(value != null);
		save.setEnabled(value != null);
		cancel.setEnabled(value != null);
		createNew.setEnabled(value == null);

		// Value can be null as well, that clears the form
		binder.readBean(value);
	}

	@Override
	public void beforeEnter(BeforeEnterEvent event) {
		if (!activeUser.hasRole(Role.ADMIN)) {
			event.forwardTo("");
		}
	}
}
