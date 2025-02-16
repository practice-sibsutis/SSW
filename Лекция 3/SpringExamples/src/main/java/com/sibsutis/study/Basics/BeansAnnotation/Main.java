package com.sibsutis.study.Basics.BeansAnnotation;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    public static void main(String[] args) {
        var context = new AnnotationConfigApplicationContext(ProjectConfig.class);

        Cat cat = context.getBean("cat3", Cat.class);
        Cat cat2 = context.getBean(Cat.class);

        System.out.println(cat.getName());
        System.out.println(cat2.getName());
    }
}
