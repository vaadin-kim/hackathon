package org.vaadin.hackme.spring.users;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class UserRepository {

	@Autowired
	JdbcTemplate jdbcTemplate;

	public User getUser(String username, String password) {
		User user = this.jdbcTemplate.queryForObject("select username, password from users where username = '"
				+ username + "' and password = '" + password + "'", new Object[] {}, new RowMapper<User>() {
					public User mapRow(ResultSet rs, int rowNum) throws SQLException {
						User user = new User();
						user.setUsername(rs.getString("username"));
						user.setPassword(rs.getString("password"));
						return user;
					}
				});

		return user;
	}

}
