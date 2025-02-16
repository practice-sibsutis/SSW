package com.sibsutis.study.Dependencies.SettersAutowired;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class Cat {
    private String name;
    private int age;
    private String color;

    @PostConstruct
    public void init(){
        System.out.println("Cat is created");
        name = "Barsik";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
