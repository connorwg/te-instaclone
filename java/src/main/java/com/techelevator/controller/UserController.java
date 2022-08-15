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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.Objects;

@RestController
@CrossOrigin
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

    @PostMapping("/profilePicture")
    public ResponseEntity<String> updateProfilePicture(Principal principal, @RequestParam("mpf") MultipartFile mpf) {

        int currentUserId = userDao.findIdByUsername(principal.getName());

        String fileName = currentUserId + "_" + Objects.requireNonNull(
                mpf.getOriginalFilename()).trim().replaceAll(" ", "_");
        String url = "https://finalprojectco.s3.us-east-2.amazonaws.com/projectimages/" + fileName;
        try {
            S3Util.uploadFile(fileName, mpf.getInputStream());
            userDao.uploadProfilePicture(currentUserId, url);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Could Not Upload. Try again");
        }
        return new ResponseEntity<>("Uploaded", HttpStatus.OK);
    }
}
