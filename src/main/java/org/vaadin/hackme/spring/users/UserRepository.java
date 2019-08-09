package org.vaadin.hackme.spring.users;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class UserRepository {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Value("${hackme.pwdsalt}")
	private String salt;

	public User getUser(String username, String password) {
		String query = "select id, username, password from users where username = '" + username + "' and password = '"
				+ hashPassword(password) + "'";
		User user = this.jdbcTemplate.queryForObject(query, new Object[] {}, new RowMapper<User>() {
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

	private String hashPassword(String clearTextPassword) {
		MessageDigest digest;
		try {
			String t = clearTextPassword + salt;
			digest = MessageDigest.getInstance("SHA-256");
			byte[] encodedhash = digest.digest(t.getBytes(StandardCharsets.UTF_8));
			return bytesToHex(encodedhash);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return "";
		}
	}
	
	private static String bytesToHex(byte[] hash) {
	    StringBuffer hexString = new StringBuffer();
	    for (int i = 0; i < hash.length; i++) {
	    String hex = Integer.toHexString(0xff & hash[i]);
	    if(hex.length() == 1) hexString.append('0');
	        hexString.append(hex);
	    }
	    return hexString.toString();
	}

}
