package com.techelevator.controller;

import com.techelevator.dao.UserDao;
import com.techelevator.model.User;
import com.techelevator.model.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@PreAuthorize("isAuthenticated()")
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
    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteUserAccount(Principal principal) {

        String username = principal.getName();
        int userId = userDao.findIdByUsername(username);

        if(!userDao.deleteUserAccount(userId)) {
            throw new UserNotFoundException();
        } else {
            userDao.deleteUserAccount(userId);
            return new ResponseEntity<>("Successfully Deleted", HttpStatus.OK);
        }
    }
}
