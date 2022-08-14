package com.techelevator.dao;

import com.techelevator.exceptions.CantFollowSelfException;
import com.techelevator.exceptions.UserNotFoundException;
import com.techelevator.model.User;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class JdbcUserDao implements UserDao {

    private final JdbcTemplate jdbcTemplate;

    public JdbcUserDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int findIdByUsername(String username) {
        if (username == null) throw new IllegalArgumentException("Username cannot be null");

        int userId;
        try {
            userId = jdbcTemplate.queryForObject("select user_id from users where username = ?", int.class, username);
        } catch (EmptyResultDataAccessException | NullPointerException e) {
            throw new UsernameNotFoundException("User " + username + " was not found.");
        }

        return userId;
    }

	@Override
	public User getUserById(int userId) {
		String sql = "SELECT * FROM users WHERE user_id = ?";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userId);
		if (results.next()) {
			return mapRowToUser(results);
		} else {
			throw new UserNotFoundException();
		}
	}

    @Override
    public List<User> findAll() {
        List<User> users = new ArrayList<>();
        String sql = "select * from users";

        SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
        while (results.next()) {
            User user = mapRowToUser(results);
            users.add(user);
        }

        return users;
    }

    @Override
    public User findByUsername(String username) {
        if (username == null) throw new IllegalArgumentException("Username cannot be null");

        for (User user : this.findAll()) {
            if (user.getUsername().equalsIgnoreCase(username)) {
                return user;
            }
        }
        throw new UsernameNotFoundException("User " + username + " was not found.");
    }

    @Override
    public boolean create(String username, String password, String role, String email, String firstName, String lastName) {
        String insertUserSql = "insert into users (username,password_hash,role, email, first_name, last_name) values (?,?,?,?,?,?)";
        String password_hash = new BCryptPasswordEncoder().encode(password);
        String ssRole = role.toUpperCase().startsWith("ROLE_") ? role.toUpperCase() : "ROLE_" + role.toUpperCase();

        return jdbcTemplate.update(insertUserSql, username, password_hash, ssRole, email, firstName, lastName) == 1;
    }

    public boolean deleteUserAccount(int userId) {

        String updateFKs =
                "BEGIN; " +
                        "UPDATE posts SET user_id = NULL WHERE user_id = ?; " +
                        "UPDATE comments SET author_id = NULL WHERE author_id = ?; " +
                        "UPDATE following SET follower_id = NULL WHERE follower_id = ?;" +
                        "UPDATE following SET followee_id = NULL WHERE followee_id = ?  " +
                        "UPDATE likes SET user_id = NULL WHERE user_id = ? " +
                        "COMMIT;";
        jdbcTemplate.update(updateFKs, userId, userId, userId, userId, userId);

        String deleteSQL = "DELETE FROM users WHERE user_id = ?;";
        if (jdbcTemplate.update(deleteSQL, userId) != 1) {
            throw new UserNotFoundException();
        }

        return jdbcTemplate.update(deleteSQL, userId) == 1;
    }

    public int followUser(int currentUserId, int userToFollowId) {

        String query = "INSERT INTO following VALUES (?, ?);";
        String unfollow = "DELETE FROM following WHERE (follower_id = ? AND followee_id = ?);";
        String checkIfFollowing = "SELECT ? in (SELECT follower_id FROM following WHERE followee_id = ?) as tf;";

        boolean alreadyFollow = Boolean.TRUE.equals(jdbcTemplate.queryForObject
                (checkIfFollowing, boolean.class, currentUserId, userToFollowId));

        List<Integer> userIds = findAll().stream().map(User::getId).collect(Collectors.toList());


        if (!userIds.contains(userToFollowId)) throw new UserNotFoundException();
        if (currentUserId == userToFollowId) throw new CantFollowSelfException();

        if (alreadyFollow) {
            jdbcTemplate.update(unfollow, currentUserId, userToFollowId);
            return 2;
        }

        jdbcTemplate.update(query, currentUserId, userToFollowId);
        return 1;
    }


    private User mapRowToUser(SqlRowSet rs) {
        User user = new User();
        user.setId(rs.getInt("user_id"));
        user.setUsername(rs.getString("username"));
        user.setEmail(rs.getString("email"));
        user.setFirstName(rs.getString("first_name"));
        user.setLastName(rs.getString("last_name"));
        user.setPassword(rs.getString("password_hash"));
        user.setProfileImage(rs.getString("profile_picture_link"));
        user.setAuthorities(Objects.requireNonNull(rs.getString("role")));
        user.setActivated(true);
        return user;
    }
}
