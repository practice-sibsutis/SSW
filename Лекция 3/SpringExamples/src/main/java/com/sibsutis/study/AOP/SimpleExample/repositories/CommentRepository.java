package com.sibsutis.study.AOP.SimpleExample.repositories;

import com.sibsutis.study.AOP.SimpleExample.model.Comment;

public interface CommentRepository {
    void storeComment(Comment comment);
}
