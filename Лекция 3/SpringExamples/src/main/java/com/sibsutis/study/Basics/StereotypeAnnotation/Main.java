package com.sibsutis.study.Basics.StereotypeAnnotation;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    public static void main(String[] args) {
        var context = new AnnotationConfigApplicationContext(ProjectConfig.class);

        Cat cat2 = context.getBean(Cat.class);

        System.out.println(cat2.getName());
    }
}
