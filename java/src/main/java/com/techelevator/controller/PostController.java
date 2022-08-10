package com.techelevator.controller;

import com.techelevator.dao.PostDao;
import com.techelevator.dao.UserDao;
import com.techelevator.model.Post;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;

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
    //get comments for a post
//    @GetMapping(value=  "/{postId}/comments")

    @PostMapping(value = "/create")
    public @ResponseBody String createPost(String description, Principal principal,
                                           @RequestParam("mpf") MultipartFile mpf) {

        int currentUserId = userDao.findIdByUsername(principal.getName());
        String fileName = currentUserId + "_" + mpf.getOriginalFilename().replaceAll(" ", "_");
        String url = "https://finalprojectco.s3.us-east-2.amazonaws.com/projectimages/" + fileName;
        try {
            S3Util.uploadFile(fileName, mpf.getInputStream());

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        postDao.createPost(currentUserId, url, description);
        return url;
    }
    //add comment to post. post id -> insert into comments
    // check PostNotFoundException comment reasonable size

}
