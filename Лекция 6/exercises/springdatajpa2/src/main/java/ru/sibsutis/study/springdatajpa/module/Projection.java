package ru.sibsutis.study.springdatajpa.module;

import org.springframework.beans.factory.annotation.Value;

public class Projection {

    public interface UserSummary {

        //The getUsername method will return the username field.
        String getUsername();

        //The getInfo method is annotated with the @Value annotation and will return the
        //concatenation of the username field, a space, and the email field.
        @Value("#{target.username} #{target.email}")
        String getInfo();

    }

    public static class UsernameOnly {
        private String username;

        public UsernameOnly(String username) {
            this.username = username;
        }

        public String getUsername() {
            return username;
        }

    }

}
