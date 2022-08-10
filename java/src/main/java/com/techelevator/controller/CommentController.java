package com.techelevator.controller;

import com.techelevator.dao.CommentDao;
import com.techelevator.dao.PostDao;
import com.techelevator.model.Comment;
import com.techelevator.model.CommentNotFoundException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@PreAuthorize("isAuthenticated()")
//@RequestMapping(value  = "/comments", method = {RequestMethod.GET, RequestMethod.POST})
public class CommentController {

    private CommentDao commentDao;

    public CommentController(CommentDao commentDao) {
        this.commentDao = commentDao;
    }

    @PostMapping(value = "comment/create")
    public Comment createComment(@RequestBody Comment comment) throws CommentNotFoundException {
        return commentDao.createComment(comment);
    }

    //FIXME: Not sure on paths for create and getCommentById, finish CRUD

    @GetMapping(value = "/{commentId}")
    public Comment getCommentByCommentId(@PathVariable int commentId) {
        return commentDao.getCommentByCommentId(commentId);
    }


    //get comments for a post
    //@GetMapping(value=  "/{postId}/comments")
    @GetMapping(value  = "/{postId}/comments")
    public Comment getCommentsByPostId(@PathVariable int postId) {
        return commentDao.getCommentsByPostId(postId);
    }

    // getCommentsByPostId

}
