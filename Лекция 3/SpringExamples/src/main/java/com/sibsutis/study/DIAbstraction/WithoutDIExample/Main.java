package com.sibsutis.study.DIAbstraction.WithoutDIExample;

import com.sibsutis.study.DIAbstraction.WithoutDIExample.model.Comment;
import com.sibsutis.study.DIAbstraction.WithoutDIExample.proxies.EmailCommentNotificationProxy;
import com.sibsutis.study.DIAbstraction.WithoutDIExample.repositories.DBCommentRepository;
import com.sibsutis.study.DIAbstraction.WithoutDIExample.services.CommentService;

public class Main {
    public static void main(String[] args) {
        var commentRepository = new DBCommentRepository();
        var commentNotificationProxy = new EmailCommentNotificationProxy();
        var commentService = new CommentService(commentRepository, commentNotificationProxy);
        var comment = new Comment();
        comment.setAuthor("Anton");
        comment.setText("Demo comment");
        commentService.publishComment(comment);
    }
}