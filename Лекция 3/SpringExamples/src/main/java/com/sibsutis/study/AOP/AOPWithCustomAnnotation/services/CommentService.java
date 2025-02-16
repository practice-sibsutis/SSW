package com.sibsutis.study.AOP.AOPWithCustomAnnotation.services;

import com.sibsutis.study.AOP.AOPWithCustomAnnotation.ToLog;
import com.sibsutis.study.AOP.AOPWithCustomAnnotation.model.Comment;
import com.sibsutis.study.AOP.AOPWithCustomAnnotation.proxies.CommentNotificationProxy;
import com.sibsutis.study.AOP.AOPWithCustomAnnotation.repositories.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service
public class CommentService {
    private CommentRepository commentRepository;
    private CommentNotificationProxy commentNotificationProxy;
    private Logger logger = Logger.getLogger(CommentService.class.getName());

    @Autowired
    public CommentService(CommentRepository commentRepository, CommentNotificationProxy commentNotificationProxy) {
        this.commentRepository = commentRepository;
        this.commentNotificationProxy = commentNotificationProxy;
    }

    public void publishComment(Comment comment) {
        commentRepository.storeComment(comment);
        logger.info("Publishing comment:" + comment.getText());
    }

    @ToLog
    public void deleteComment(Comment comment) {
        logger.info("Deleting comment:" + comment.getText());
    }

    public void editComment(Comment comment) {
        logger.info("Editing comment:" + comment.getText());
    }
}
