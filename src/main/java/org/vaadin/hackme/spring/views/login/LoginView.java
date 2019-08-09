package org.vaadin.hackme.spring.views.login;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.vaadin.hackme.spring.users.ActiveUser;
import org.vaadin.hackme.spring.users.User;
import org.vaadin.hackme.spring.users.UserRepository;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.Notification.Position;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.server.VaadinService;
import com.vaadin.flow.spring.annotation.UIScope;

@UIScope
@Component
public class LoginView extends HorizontalLayout implements ComponentEventListener<ClickEvent<Button>> {
	
	private TextField username = new TextField();
	private PasswordField password = new PasswordField();
	private Button loginButton = new Button("Login");

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ActiveUser activeUser;
	
	@PostConstruct
	private void setUp() {
		setMargin(true);
		username.setPlaceholder("Username");
		password.setPlaceholder("Password");
		loginButton.setThemeName("primary");
		
		add(username, password, loginButton);
		loginButton.addClickListener(this);
	}

	@Override
	public void onComponentEvent(ClickEvent<Button> event) {
		try {
			User user = userRepository.getUser(username.getValue(), password.getValue());
			activeUser.setUser(user);
			username.clear();
			password.clear();
			// Reinitialize session to prevent session fixation attacks
			VaadinService.reinitializeSession(VaadinService.getCurrentRequest());
		} catch (Exception e) {
			password.clear();
			Notification notification = new Notification(
			        "Invalid username or password", 3000, Position.TOP_END);
			notification.open();
			username.clear();
			password.clear();
		}
	}

}
