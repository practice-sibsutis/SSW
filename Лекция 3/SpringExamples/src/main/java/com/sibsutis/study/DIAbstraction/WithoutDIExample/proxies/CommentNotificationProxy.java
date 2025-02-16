package com.sibsutis.study.DIAbstraction.WithoutDIExample.proxies;

import com.sibsutis.study.DIAbstraction.WithoutDIExample.model.Comment;

public interface CommentNotificationProxy {
    void sendComment(Comment comment);
}
