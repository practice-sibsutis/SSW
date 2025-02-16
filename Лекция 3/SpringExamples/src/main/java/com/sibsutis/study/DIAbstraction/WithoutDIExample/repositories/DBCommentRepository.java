package com.sibsutis.study.DIAbstraction.WithoutDIExample.repositories;

import com.sibsutis.study.DIAbstraction.WithoutDIExample.model.Comment;

public class DBCommentRepository implements CommentRepository {

    @Override
    public void storeComment(Comment comment) {
        System.out.println("Storing comment: " + comment.getText());
    }
}
