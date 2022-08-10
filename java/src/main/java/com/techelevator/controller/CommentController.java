package com.techelevator.controller;

import com.techelevator.dao.CommentDao;
import com.techelevator.dao.UserDao;
import com.techelevator.exceptions.CommentNotFoundException;
import com.techelevator.model.Comment;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

@RestController
@CrossOrigin
@PreAuthorize("isAuthenticated()")

public class CommentController {

    private CommentDao commentDao;
    private UserDao userDao;

    public CommentController(CommentDao commentDao, UserDao userDao) {
        this.commentDao = commentDao;
        this.userDao = userDao;
    }


    @PostMapping(value = "post/{postId}/create")
    public Comment createComment(@RequestParam("comment") @Valid String comment, @PathVariable int postId, Principal principal) throws CommentNotFoundException {
        int currentAuthorId = userDao.findIdByUsername(principal.getName());
        return commentDao.createComment(comment, postId, currentAuthorId);
    }

    //FIXME: Not sure on paths for create and getCommentById, finish CRUD


    @GetMapping(value = "/{commentId}")
    public Comment getCommentByCommentId(@PathVariable int commentId) {
        return commentDao.getCommentByCommentId(commentId);
    }


    @GetMapping(value  = "/post/{postId}/comments")
    public Comment getCommentsByPostId(@Valid @PathVariable int postId) {
        return commentDao.getCommentsByPostId(postId);
    }



}
