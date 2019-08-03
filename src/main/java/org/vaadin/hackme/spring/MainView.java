package org.vaadin.hackme.spring;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.hackme.spring.views.login.LoginView;
import org.vaadin.hackme.spring.views.news.NewsView;
import org.vaadin.hackme.spring.views.publishing.PublishingView;
import org.vaadin.hackme.spring.views.usermanagement.UserManagementView;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.TabVariant;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.server.PWA;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;

/**
 * The main view is a top-level placeholder for other views.
 */
@JsModule("./styles/shared-styles.js")
@PWA(name = "Hackathon", shortName = "Hackathon")
@Theme(value = Lumo.class, variant = Lumo.DARK)
public class MainView extends AppLayout {

	private final Tabs menu;
    
    @Autowired
    private LoginView loginView;

    public MainView() {
        menu = createMenuTabs();
    }
    
    @PostConstruct
    private void setUp() {
    	HorizontalLayout topLayout = new HorizontalLayout();
    	topLayout.setWidth("100%");
    	topLayout.add(menu, loginView);
    	topLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.BETWEEN);
    	topLayout.setDefaultVerticalComponentAlignment(Alignment.END);
    	topLayout.setFlexGrow(1, menu);
    	addToNavbar(topLayout);
    }

    private static Tabs createMenuTabs() {
        final Tabs tabs = new Tabs();
        tabs.setOrientation(Tabs.Orientation.HORIZONTAL);
        tabs.add(getAvailableTabs());
        return tabs;
    }

    private static Tab[] getAvailableTabs() {
        final List<Tab> tabs = new ArrayList<>();
        tabs.add(createTab("News", VaadinIcon.COMMENTS, NewsView.class));
        tabs.add(createTab("Publishing", VaadinIcon.EDIT, PublishingView.class));
        tabs.add(createTab("Users", VaadinIcon.GROUP, UserManagementView.class));
        return tabs.toArray(new Tab[tabs.size()]);
    }

    private static Tab createTab(String title, VaadinIcon icon,
            Class<? extends Component> viewClass) {
    	RouterLink routerLink = new RouterLink(null, viewClass);
    	routerLink.add(icon.create());
    	routerLink.add(title);
    	
        return createTab(routerLink);
    }

    private static Tab createTab(Component content) {
        final Tab tab = new Tab();
        tab.addThemeVariants(TabVariant.LUMO_ICON_ON_TOP);
        tab.add(content);
        return tab;
    }
}
