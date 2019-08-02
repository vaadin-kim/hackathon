package org.vaadin.hackme.spring;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasComponents;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.TabVariant;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.server.PWA;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;

import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.hackme.spring.views.login.LoginView;
import org.vaadin.hackme.spring.views.news.NewsView;
import org.vaadin.hackme.spring.views.publishing.PublishingView;
import org.vaadin.hackme.spring.views.usermanagement.UserManagementView;

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
        addToNavbar(menu);
    }
    
    @PostConstruct
    private void setUp() {
    	addToNavbar(loginView);
    }

    private static Tabs createMenuTabs() {
        final Tabs tabs = new Tabs();
        tabs.setOrientation(Tabs.Orientation.HORIZONTAL);
        tabs.add(getAvailableTabs());
        return tabs;
    }

    private static Tab[] getAvailableTabs() {
        final List<Tab> tabs = new ArrayList<>();
        tabs.add(createTab("News", NewsView.class));
        tabs.add(createTab("Publishing", PublishingView.class));
        tabs.add(createTab("User Management", UserManagementView.class));
        return tabs.toArray(new Tab[tabs.size()]);
    }

    private static Tab createTab(String title,
            Class<? extends Component> viewClass) {
        return createTab(populateLink(new RouterLink(null, viewClass), title));
    }

    private static Tab createTab(Component content) {
        final Tab tab = new Tab();
        tab.addThemeVariants(TabVariant.LUMO_ICON_ON_TOP);
        tab.add(content);
        return tab;
    }

    private static <T extends HasComponents> T populateLink(T a, String title) {
        a.add(title);
        return a;
    }

}
