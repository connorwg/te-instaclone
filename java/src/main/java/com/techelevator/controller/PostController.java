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

import java.security.Principal;
import java.util.Objects;

@RestController
@CrossOrigin
@PreAuthorize("isAuthenticated()")
@RequestMapping(value = "/post", method = {RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE})
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
    @ResponseBody
    public Post createPost(@RequestParam("desc") String desc,
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
    public boolean likePost(Principal principal, @RequestParam(defaultValue = "postId", value = "postId") int postId) {

        int userId = userDao.findIdByUsername(principal.getName());

        return postDao.likePost(userId, postId);
    }

    @GetMapping("/liked")
    public boolean userLikedPost(Principal principal, @RequestParam("postId") int postId) {

        int userId = userDao.findIdByUsername(principal.getName());
        return postDao.userLikedPost(userId, postId);
    }

    @GetMapping("/likes")
    public int postLikes(@RequestParam("postId") int postId) {

        return postDao.likesCount(postId);
    }


    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public ResponseEntity<String> deleteCollection(@RequestParam("postIds") int[] postIds) {
        postDao.deletePostCollection(postIds);
        return new ResponseEntity<>("Deleted", HttpStatus.OK);
    }

    @PostMapping("/{postId}/favorite")
    public boolean favoritePost(@PathVariable int postId, Principal principal) {
        int currentUserId = userDao.findIdByUsername(principal.getName());
//        favorite a post
    }

    @GetMapping("/favorites")
    public boolean favoritePost(Principal principal) {
        int currentUserId = userDao.findIdByUsername(principal.getName());
//        get all favorites
    }

}
