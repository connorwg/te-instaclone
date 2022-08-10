package com.techelevator.dao;

import com.techelevator.model.Comment;

public interface CommentDao {

    Comment getCommentById(int commentId);

    Comment createComment(Comment comment);

    Comment getCommentByPostId(int postId);
}
