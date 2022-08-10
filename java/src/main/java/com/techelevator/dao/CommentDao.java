package com.techelevator.dao;

import com.techelevator.model.Comment;

public interface CommentDao {

    Comment getCommentByCommentId(int commentId);

    Comment createComment(String comment, int post_id, int author_id);

    Comment getCommentsByPostId(int postId);


}
