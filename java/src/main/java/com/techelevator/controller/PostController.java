package com.techelevator.controller;

import com.techelevator.dao.PostDao;
import com.techelevator.dao.UserDao;
import com.techelevator.model.Post;
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
@RequestMapping(value = "/post", method = {RequestMethod.GET, RequestMethod.POST})
public class PostController {

    private PostDao postDao;
    private UserDao userDao;

    public PostController(PostDao postDao, UserDao userDao) {
        this.postDao = postDao;
        this.userDao = userDao;
    }

    @GetMapping(value = "/{postId}")
    public Post getPostById(@PathVariable int postId) {
        return postDao.getPostById(postId);
    }


    @PostMapping(value = "/create")
    public @ResponseBody Post createPost(@RequestParam("desc") String desc,
                                         Principal principal, @RequestParam("mpf") MultipartFile mpf) {

        int currentUserId = userDao.findIdByUsername(principal.getName());

        String fileName = currentUserId + "_" + Objects.requireNonNull(
                mpf.getOriginalFilename()).trim().replaceAll(" ", "_");

        String url = "https://finalprojectco.s3.us-east-2.amazonaws.com/projectimages/" + fileName;

        try {
            S3Util.uploadFile(fileName, mpf.getInputStream());

        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Could Not Upload. Try again");
        }

        return postDao.createPost(currentUserId, url, desc);
    }

    @PostMapping("/like")
    @ResponseBody
    public ResponseEntity<String> likePost(Principal principal, @RequestParam(defaultValue = "postId", value = "postId") int postId) {

        int userId = userDao.findIdByUsername(principal.getName());
        int response = postDao.likePost(userId, postId);

        if (response == 2) {
            return new ResponseEntity<>("Unliked", HttpStatus.OK);
        }
        if (response == 1) {
            return new ResponseEntity<>("Liked", HttpStatus.OK);
        }

        return new ResponseEntity<>("oof", HttpStatus.BAD_REQUEST);
    }





}
