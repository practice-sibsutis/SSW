package com.sibsutis.study.AOP.SimpleExample;

import com.sibsutis.study.AOP.SimpleExample.model.Comment;
import com.sibsutis.study.AOP.SimpleExample.services.CommentService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    public static void main(String[] args) {
        var context = new AnnotationConfigApplicationContext(ProjectConfiguration.class);
        var commentService = context.getBean(CommentService.class);
        var comment = new Comment();
        comment.setAuthor("Anton");
        comment.setText("Demo comment");
        commentService.publishComment(comment);
    }
}