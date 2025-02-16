package com.sibsutis.study.DIAbstraction.WithDIExample.services;

import com.sibsutis.study.DIAbstraction.WithDIExample.model.Comment;
import com.sibsutis.study.DIAbstraction.WithDIExample.proxies.CommentNotificationProxy;
import com.sibsutis.study.DIAbstraction.WithDIExample.repositories.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentService {
    private CommentRepository commentRepository;

    private CommentNotificationProxy commentNotificationProxy;

    @Autowired
    public CommentService(CommentRepository commentRepository, CommentNotificationProxy commentNotificationProxy) {
        this.commentRepository = commentRepository;
        this.commentNotificationProxy = commentNotificationProxy;
    }

    public void publishComment(Comment comment) {
        commentRepository.storeComment(comment);
        commentNotificationProxy.sendComment(comment);
    }
}
