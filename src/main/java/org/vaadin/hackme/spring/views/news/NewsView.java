package org.vaadin.hackme.spring.views.news;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.vaadin.hackme.spring.backend.BackendService;
import org.vaadin.hackme.spring.backend.Employee;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.polymertemplate.PolymerTemplate;
import com.vaadin.flow.router.AfterNavigationEvent;
import com.vaadin.flow.router.AfterNavigationObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.templatemodel.TemplateModel;

import org.vaadin.hackme.spring.MainView;
import org.vaadin.hackme.spring.views.news.NewsView.NewsViewModel;

@Route(value = "news", layout = MainView.class)
@RouteAlias(value = "", layout = MainView.class)
@PageTitle("News")
@JsModule("src/views/news/news-view.js")
@Tag("news-view")
public class NewsView extends PolymerTemplate<NewsViewModel>
        implements AfterNavigationObserver {

    public static interface NewsViewModel extends TemplateModel {
        public void setPersons(List<Employee> items);
    }

    @Autowired
    private BackendService service;

    public NewsView() {
    }

    @Override
    public void afterNavigation(AfterNavigationEvent event) {
        // Lazy init of the grid items, happens only when we are sure the view will be
        // shown to the user

        // In this class we use the TemplateModel to populate data since we don't need
        // to reference the Grid in Java code. For that, please see MasterDetailView
        getModel().setPersons(service.getEmployees());
    }
}
