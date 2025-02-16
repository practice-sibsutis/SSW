package com.sibsutis.study.AOP.SimpleExample.repositories;

import com.sibsutis.study.AOP.SimpleExample.model.Comment;
import org.springframework.stereotype.Repository;

@Repository
public class DBCommentRepository implements CommentRepository {

    @Override
    public void storeComment(Comment comment) {
        System.out.println("Storing comment: " + comment.getText());
    }
}
