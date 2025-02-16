package com.sibsutis.study.AOP.AOPWithCustomAnnotation.repositories;

import com.sibsutis.study.AOP.AOPWithCustomAnnotation.model.Comment;

public interface CommentRepository {
    void storeComment(Comment comment);
}
