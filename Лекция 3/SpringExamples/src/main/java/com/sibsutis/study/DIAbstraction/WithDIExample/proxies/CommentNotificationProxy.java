package com.sibsutis.study.DIAbstraction.WithDIExample.proxies;

import com.sibsutis.study.DIAbstraction.WithDIExample.model.Comment;

public interface CommentNotificationProxy {
    void sendComment(Comment comment);
}
