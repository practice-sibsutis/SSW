package com.sibsutis.study.AOP.AOPWithCustomAnnotation.proxies;

import com.sibsutis.study.AOP.AOPWithCustomAnnotation.model.Comment;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@Qualifier("EMAIL")
public class EmailCommentNotificationProxy
        implements CommentNotificationProxy {
    @Override
    public void sendComment(Comment comment) {
        System.out.println("Sending notification for comment: "
                + comment.getText());
    }
}
