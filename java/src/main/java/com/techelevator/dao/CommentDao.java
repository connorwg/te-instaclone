package com.techelevator.dao;

import com.techelevator.model.Comment;

import java.util.List;

public interface CommentDao {

    Comment getCommentByCommentId(int commentId);

    int createComment(String comment, int post_id, int author_id);

    List<Comment> getCommentsByPostId(int postId);


}
