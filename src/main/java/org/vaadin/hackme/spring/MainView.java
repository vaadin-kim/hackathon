package org.vaadin.hackme.spring;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.hackme.spring.users.ActiveUser;
import org.vaadin.hackme.spring.views.login.LoginView;
import org.vaadin.hackme.spring.views.news.NewsView;
import org.vaadin.hackme.spring.views.publishing.PublishingView;
import org.vaadin.hackme.spring.views.usermanagement.UserManagementView;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.TabVariant;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.server.PWA;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;

@SuppressWarnings("serial")
@JsModule("./styles/shared-styles.js")
@PWA(name = "Hackathon", shortName = "Hackathon")
@Theme(value = Lumo.class, variant = Lumo.DARK)
public class MainView extends AppLayout implements PropertyChangeListener {

	private Tabs menu;

	@Autowired
	private LoginView loginView;

	@Autowired
	private ActiveUser activeUser;

	private HorizontalLayout topLayout;

	private Button logoutButton;

	@PostConstruct
	private void setUp() {
		activeUser.addPropertyChangeListener(this);

		menu = createMenuTabs();

		topLayout = new HorizontalLayout();
		topLayout.setHeight("70px");
		topLayout.setWidth("100%");
		topLayout.add(menu, loginView);
		topLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.BETWEEN);
		topLayout.setDefaultVerticalComponentAlignment(Alignment.END);
		topLayout.setFlexGrow(1, menu);
		addToNavbar(topLayout);
		System.out.println("[Adding] Top layout: " + System.identityHashCode(topLayout) + " Login layout: " + System.identityHashCode(loginView));
	}

	private static Tabs createMenuTabs() {
		final Tabs tabs = new Tabs();
		tabs.setOrientation(Tabs.Orientation.HORIZONTAL);
		tabs.add(createTab("News", VaadinIcon.COMMENTS, NewsView.class));
		return tabs;
	}

	private static Tab createTab(String title, VaadinIcon icon, Class<? extends Component> viewClass) {
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

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if (evt.getNewValue() != null) {
			System.out.println("[Removing] Top layout: " + System.identityHashCode(topLayout) + " Login layout: " + System.identityHashCode(loginView));
			topLayout.remove(loginView);

			menu.add(createTab("Publishing", VaadinIcon.EDIT, PublishingView.class));
			menu.add(createTab("Users", VaadinIcon.GROUP, UserManagementView.class));

			if (logoutButton == null) {
				logoutButton = new Button("Logout", event -> {
					activeUser.logout();
					getUI().get().navigate("");
					getUI().get().getSession().close();
				});
			}

			// I'm lazy and adding a wrapper layout just to get the margins there for the
			// logout button to be positioned correctly
			HorizontalLayout dirtyHackLayout = new HorizontalLayout(logoutButton);
			dirtyHackLayout.setMargin(true);
			topLayout.add(dirtyHackLayout);
		} else {
			// Remove logout button
			topLayout.remove(topLayout.getComponentAt(1));
			topLayout.add(loginView);

			// Remove publishing and users from navigation
			menu.remove(menu.getComponentAt(2));
			menu.remove(menu.getComponentAt(1));
		}
	}
}
