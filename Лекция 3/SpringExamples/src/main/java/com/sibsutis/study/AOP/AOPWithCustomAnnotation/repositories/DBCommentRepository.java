package com.sibsutis.study.AOP.AOPWithCustomAnnotation.repositories;

import com.sibsutis.study.AOP.AOPWithCustomAnnotation.model.Comment;
import org.springframework.stereotype.Repository;

@Repository
public class DBCommentRepository implements CommentRepository {

    @Override
    public void storeComment(Comment comment) {
        System.out.println("Storing comment: " + comment.getText());
    }
}
