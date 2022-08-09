package com.techelevator.controller;

import com.techelevator.dao.PostDao;
import com.techelevator.model.Post;
import com.techelevator.model.PostNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class PostController {

    private PostDao postDao;

    public PostController(PostDao postDao) {
        this.postDao = postDao;
    }

    @RequestMapping(value = "/post/{postId}", method = RequestMethod.GET)
    public Post getPostById(@PathVariable int postId) {
        return postDao.getPostById(postId);
    }

    @RequestMapping(value = "/post", method = RequestMethod.POST)
    public Post createPost(@RequestBody Post post) throws PostNotFoundException  {
        return postDao.createPost(post);
    }


}
