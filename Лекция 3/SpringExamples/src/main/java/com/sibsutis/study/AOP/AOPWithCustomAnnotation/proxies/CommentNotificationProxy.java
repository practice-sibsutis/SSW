package com.sibsutis.study.AOP.AOPWithCustomAnnotation.proxies;

import com.sibsutis.study.AOP.AOPWithCustomAnnotation.model.Comment;

public interface CommentNotificationProxy {
    void sendComment(Comment comment);
}
