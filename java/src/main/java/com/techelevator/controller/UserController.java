package com.techelevator.controller;

import com.techelevator.dao.PostDao;
import com.techelevator.dao.UserDao;
import com.techelevator.model.Post;
import com.techelevator.model.User;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@RestController
@PreAuthorize("isAuthenticated()")
@RequestMapping(value = "/user", method = {RequestMethod.GET, RequestMethod.DELETE})
public class UserController {

    private UserDao userDao;
    private PostDao postDao;


    public UserController(UserDao userDao, PostDao postDao) {
        this.userDao = userDao;
        this.postDao = postDao;
    }

    @GetMapping(value = "/{userId}")
    public User getUserById(@PathVariable int userId) {
        return userDao.getUserById(userId);
    }


    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteUserAccount(@NotNull Principal principal) {

        int userId = userDao.findIdByUsername(principal.getName());

        if (userDao.deleteUserAccount(userId)) {
            return new ResponseEntity<>("Successfully Deleted", HttpStatus.OK);
        }

        return new ResponseEntity<>("Fatal Error.", HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/{userId}/follow")
    public ResponseEntity<String> followUser(@NotNull Principal principal, @PathVariable int userId) {

        int currentUserId = userDao.findIdByUsername(principal.getName());
        int response = userDao.followUser(currentUserId, userId);

        if (response == 1) {
            return new ResponseEntity<>("Followed User.", HttpStatus.OK);
        }
        if (response == 2) {
            return new ResponseEntity<>("Unfollowed User.", HttpStatus.OK);
        }
        return new ResponseEntity<>("Fatal Error.", HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/{userId}/profile")
    public List<Post> getAllPostsByUserId(@Valid @PathVariable int userId) {
        return postDao.getAllPostsByUserId(userId);
    }
}
