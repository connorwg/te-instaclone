package com.techelevator.controller;

import com.techelevator.dao.PostDao;
import com.techelevator.dao.UserDao;
import com.techelevator.model.Post;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController
@PreAuthorize("isAuthenticated()")
public class FeedController {

    private PostDao postDao;
    private UserDao userDao;

    public FeedController(PostDao postDao, UserDao userDao) {
        this.postDao = postDao;
        this.userDao = userDao;
    }

    @GetMapping(value = "/feed")
    public List<Post> getAllFolloweePostsByFollowerId(Principal principal) {

        int userId = userDao.findIdByUsername(principal.getName());

        return postDao.getAllFolloweePostsByFollowerId(userId);
    }

    @PreAuthorize("permitAll()")
    @GetMapping(value = "/landing-page")
    public List<Post> findAllPosts() {
        return postDao.findAllPosts();
    }

}





