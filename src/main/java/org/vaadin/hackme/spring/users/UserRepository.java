package org.vaadin.hackme.spring.users;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class UserRepository {

	@Autowired
	JdbcTemplate jdbcTemplate;

	public User getUser(String username, String password) {
		User user = this.jdbcTemplate.queryForObject("select id, username, password from users where username = '"
				+ username + "' and password = '" + password + "'", new Object[] {}, new RowMapper<User>() {
					public User mapRow(ResultSet rs, int rowNum) throws SQLException {
						User user = new User();
						user.setId(rs.getLong("id"));
						user.setUsername(rs.getString("username"));
						user.setPassword(rs.getString("password"));
						return user;
					}
				});

		if (user != null) {
			List<Role> roles = this.jdbcTemplate.query("select role from role_mapping where user_id=?",
					new Object[] { user.getId() }, new RowMapper<Role>() {
						public Role mapRow(ResultSet rs, int rowNum) throws SQLException {
							String roleName = rs.getString("role");
							return Role.getRole(roleName);
						}
					});
			user.setRoles(roles);
		}

		return user;
	}

}
