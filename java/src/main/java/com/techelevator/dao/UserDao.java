package com.techelevator.dao;

import com.techelevator.model.User;

import java.util.List;

public interface UserDao {

    public List<User> findAll();

    public User getUserById(int userId);

    public User findByUsername(String username);

    public int findIdByUsername(String username);

    public boolean create(String username, String password, String role, String email, String firstName, String lastName);

    public boolean deleteUserAccount(int userId);

    public int followUser(int currentUserId, int userToFollowId);
}
