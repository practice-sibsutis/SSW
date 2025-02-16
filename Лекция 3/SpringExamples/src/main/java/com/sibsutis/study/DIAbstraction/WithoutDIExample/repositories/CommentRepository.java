package com.sibsutis.study.DIAbstraction.WithoutDIExample.repositories;

import com.sibsutis.study.DIAbstraction.WithoutDIExample.model.Comment;

public interface CommentRepository {
    void storeComment(Comment comment);
}
