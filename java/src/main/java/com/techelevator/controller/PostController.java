package com.techelevator.controller;

import com.techelevator.dao.PostDao;
import com.techelevator.model.Post;
import com.techelevator.model.PostNotFoundException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@PreAuthorize("isAuthenticated()")
@RequestMapping(value = "/post", method = {RequestMethod.GET, RequestMethod.POST})
public class PostController {

    private PostDao postDao;

    public PostController(PostDao postDao) {
        this.postDao = postDao;
    }

    @GetMapping(value = "/{postId}")
    public Post getPostById(@PathVariable int postId) {
        return postDao.getPostById(postId);
    }


    @PostMapping(value = "/create")
    public Post createPost(@RequestBody Post post) throws PostNotFoundException  {
        return postDao.createPost(post);
    }




    //add comment to post. post id -> insert into comments
    // check PostNotFoundException comment reasonable size

}
