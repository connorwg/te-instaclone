package com.techelevator.controller;

import com.techelevator.dao.UserDao;
import com.techelevator.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;

@RestController
@RequestMapping(value = "/user", method = {RequestMethod.GET, RequestMethod.DELETE})
public class UserController {

    private UserDao userDao;

    public UserController(UserDao userDao) {
        this.userDao = userDao;
    }

    @GetMapping(value = "/{userId}")
    public User getUserById(@PathVariable int userId) {
        return userDao.getUserById(userId);
    }
    //could be different path for a delete page
    @DeleteMapping("/delete/{userId}")
    public void deleteUserAccount(@PathVariable int userId) {

        if(!userDao.deleteUserAccount(userId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User Not Found");
        } else {
            userDao.deleteUserAccount(userId);
            throw new ResponseStatusException(HttpStatus.ACCEPTED, "User Deleted");

        }
    }
}
