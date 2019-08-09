package org.vaadin.hackme.spring.users;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.vaadin.flow.spring.annotation.UIScope;
import com.vaadin.flow.spring.annotation.VaadinSessionScope;

@Component
@UIScope
public class ActiveUser {
    private User user;
    
    private List<PropertyChangeListener> listeneres = new ArrayList<>();

	public User getUser() {
		return user;
	}
	
	public boolean isLoggedIn() {
		return user != null;
	}
	
	public void logout() {
		this.setUser(null);
	}

	public void setUser(User user) {
		PropertyChangeEvent event = new PropertyChangeEvent(this, "user", this.user, user);
		this.user = user;
		
		this.listeneres.forEach(l -> l.propertyChange(event));
	}
	
	public void addPropertyChangeListener(PropertyChangeListener listener) {
		this.listeneres.add(listener);
	}
	
	public void removePropertyChangeListener(PropertyChangeListener listener) {
		this.listeneres.remove(listener);
	}
	
	public boolean hasRole(Role role) {
		if(!isLoggedIn()) 
			return false;
		
		return getUser().getRoles().contains(role);
	}

}
