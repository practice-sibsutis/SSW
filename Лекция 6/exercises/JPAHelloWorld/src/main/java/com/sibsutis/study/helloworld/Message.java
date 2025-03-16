package com.sibsutis.study.helloworld;

import jakarta.persistence.*;

@Entity
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String text;
    public String getText() {
        return text;
    }
    public void setText(String text) {
        this.text = text;
    }
}
