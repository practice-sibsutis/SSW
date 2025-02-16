package com.sibsutis.study.DIAbstraction.WithoutDIExample.proxies;

import com.sibsutis.study.DIAbstraction.WithoutDIExample.model.Comment;

public class EmailCommentNotificationProxy
        implements CommentNotificationProxy {
    @Override
    public void sendComment(Comment comment) {
        System.out.println("Sending notification for comment: "
                + comment.getText());
    }
}
