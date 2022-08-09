package com.techelevator.controller;

import com.techelevator.dao.UserDao;
import com.techelevator.model.User;
import com.techelevator.model.UserNotFoundException;
import org.jetbrains.annotations.NotNull;
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
    public ResponseEntity<String> deleteUserAccount(@NotNull Principal principal) {

        String username = principal.getName();
        int userId = userDao.findIdByUsername(username);

        if(!userDao.deleteUserAccount(userId)) {
            throw new UserNotFoundException();
        } else {
            userDao.deleteUserAccount(userId);
            return new ResponseEntity<>("Successfully Deleted", HttpStatus.OK);
        }
    }
    @GetMapping("/{userId}/follow")
    public ResponseEntity<String> followUser(@NotNull Principal principal, @PathVariable int userId) {

        int currentUserId = userDao.findIdByUsername(principal.getName());

        int response = userDao.followUser(currentUserId, userId);
        if(response == -1) { throw new UserNotFoundException();}
        if(response == -2) {return new ResponseEntity<>("Can't Follow Self.", HttpStatus.BAD_REQUEST );}
        if(response == -3) {return new ResponseEntity<>("Already Follow User.", HttpStatus.BAD_REQUEST);}
        if(response == 1) { return new ResponseEntity<>("Followed User.", HttpStatus.OK);}
        return new ResponseEntity<>("Random Error.", HttpStatus.BAD_REQUEST);
    }
}
