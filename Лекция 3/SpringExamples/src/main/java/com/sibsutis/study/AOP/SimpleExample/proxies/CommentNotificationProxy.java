package com.sibsutis.study.AOP.SimpleExample.proxies;

import com.sibsutis.study.AOP.SimpleExample.model.Comment;

public interface CommentNotificationProxy {
    void sendComment(Comment comment);
}
