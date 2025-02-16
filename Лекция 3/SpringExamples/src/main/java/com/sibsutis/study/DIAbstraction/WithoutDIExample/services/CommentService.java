package com.sibsutis.study.DIAbstraction.WithoutDIExample.services;

import com.sibsutis.study.DIAbstraction.WithoutDIExample.proxies.CommentNotificationProxy;
import com.sibsutis.study.DIAbstraction.WithoutDIExample.repositories.CommentRepository;
import com.sibsutis.study.DIAbstraction.WithoutDIExample.model.Comment;

public class CommentService {
    private final CommentRepository commentRepository;
    private final CommentNotificationProxy commentNotificationProxy;

    public CommentService(CommentRepository commentRepository, CommentNotificationProxy commentNotificationProxy) {
        this.commentRepository = commentRepository;
        this.commentNotificationProxy = commentNotificationProxy;
    }

    public void publishComment(Comment comment) {
        commentRepository.storeComment(comment);
        commentNotificationProxy.sendComment(comment);
    }
}
