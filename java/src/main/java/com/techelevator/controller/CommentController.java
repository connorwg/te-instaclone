package com.techelevator.controller;

import com.techelevator.dao.CommentDao;
import com.techelevator.dao.PostDao;
import com.techelevator.model.Comment;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@PreAuthorize("isAuthenticated()")
@RequestMapping(value = "/comments", method = {RequestMethod.GET, RequestMethod.POST})
public class CommentController {

    private CommentDao commentDao;

    public CommentController(CommentDao commentDao) {
        this.commentDao = commentDao;
    }

//FIXME: Not sure on paths for create and getCommentById, finish CRUD

    @GetMapping(value = "/{commentId}")
    public Comment getCommentById(@PathVariable int commentId) {
        return commentDao.getCommentById(commentId);
    }

    @GetMapping(value  = "/{postId}/comments")
    public Comment getCommentByPostId(@PathVariable int postId) {
        return commentDao.getCommentByPostId(postId);
    }

    // getCommentsByPostId
    @PostMapping(value = "comment/create")
    public Comment createComment(@RequestBody Comment comment) {
        return commentDao.createComment(comment);
    }
}
