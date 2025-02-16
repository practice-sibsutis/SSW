package com.sibsutis.study.DIAbstraction.WithDIExample.repositories;

import com.sibsutis.study.DIAbstraction.WithDIExample.model.Comment;

public interface CommentRepository {
    void storeComment(Comment comment);
}
