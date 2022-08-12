package com.techelevator.controller;

import com.techelevator.dao.FollowDao;
import com.techelevator.dao.PostDao;
import com.techelevator.model.Post;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@PreAuthorize("isAuthenticated()")
public class FeedController {

    private PostDao postDao;

    public FeedController(PostDao postDao) {
        this.postDao = postDao;
    }

    @GetMapping(value = "/feed")
    public List<Post> getAllFolloweePostsByFollowerId(@PathVariable int followerId) {
        return postDao.getAllFolloweePostsByFollowerId(followerId);
    }


}





