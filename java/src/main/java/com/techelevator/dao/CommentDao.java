package com.techelevator.dao;

import com.techelevator.model.Comment;

public interface CommentDao {

    Comment getCommentByCommentId(int commentId);

    Comment createComment(Comment comment);

    Comment getCommentsByPostId(int postId);
}
