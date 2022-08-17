package com.techelevator.controller;

import com.techelevator.dao.CommentDao;
import com.techelevator.dao.UserDao;
import com.techelevator.exceptions.CommentNotFoundException;
import com.techelevator.exceptions.PostNotFoundException;
import com.techelevator.model.Comment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

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
    public ResponseEntity<String> createComment(@RequestParam("comment") @Valid String comment,
                                                @PathVariable int postId, Principal principal) throws CommentNotFoundException {

        int currentAuthorId = userDao.findIdByUsername(principal.getName());
        int response = commentDao.createComment(comment, postId, currentAuthorId);

        if (response == -1) {
            throw new PostNotFoundException();
        }
        return new ResponseEntity<>("Comment Posted", HttpStatus.CREATED);
    }



    @GetMapping(value = "/{commentId}")
    public Comment getCommentByCommentId(@PathVariable int commentId) {
        return commentDao.getCommentByCommentId(commentId);
    }


    @GetMapping(value = "/post/{postId}/comments")
    public List<Comment> getCommentsByPostId(@Valid @PathVariable int postId) {
        List<Comment> toReturn = commentDao.getCommentsByPostId(postId);
        for(Comment element : toReturn) {
            element.setUsername(userDao.getUserById(element.getAuthor_id()).getUsername());
        }

        return toReturn;
    }
}
