package com.sibsutis.study.Dependencies.SettersAutowired;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    public static void main(String[] args) {
        var context = new AnnotationConfigApplicationContext(ProjectConfig.class);

        Person person = context.getBean(Person.class);

        System.out.println(person.getName());
        System.out.println("Person's cat: " + person.getCat().getName());
    }
}
