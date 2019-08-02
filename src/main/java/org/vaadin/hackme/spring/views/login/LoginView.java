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
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.dom.Element;
import com.vaadin.flow.dom.ElementFactory;
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
		add(username, password, loginButton);
		loginButton.addClickListener(this);
	}

	@Override
	public void onComponentEvent(ClickEvent<Button> event) {
		try {
			User user = userRepository.getUser(username.getValue(), password.getValue());
			activeUser.setUser(user);
		} catch (Exception e) {
			password.clear();
			Element label = ElementFactory.createLabel("Incorrect username or password");
			getElement().removeAllChildren();
			getElement().appendChild(label);
		}
	}

}
