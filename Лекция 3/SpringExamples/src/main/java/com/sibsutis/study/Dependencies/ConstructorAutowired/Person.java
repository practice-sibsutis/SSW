package com.sibsutis.study.Dependencies.ConstructorAutowired;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class Person {
    private String name;
    private int age;
    private Cat cat;

    @Autowired
    public Person(Cat cat) {
        this.cat = cat;
    }

    @PostConstruct
    public void init(){
        System.out.println("Person init");
        name = "Anton";
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

    public Cat getCat() {
        return cat;
    }

    public void setCat(Cat cat) {
        this.cat = cat;
    }
}
