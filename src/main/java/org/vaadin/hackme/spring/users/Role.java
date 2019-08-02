package org.vaadin.hackme.spring.users;

public enum Role {
	NORMAL, ADMIN;

	public static Role getRole(String name) {
		for (Role role : Role.values()) {
			if (role.name().equals(name.toUpperCase())) {
				return role;
			}
		}

		return null;
	}
}
