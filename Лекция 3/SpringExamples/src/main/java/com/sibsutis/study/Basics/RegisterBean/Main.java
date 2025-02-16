package com.sibsutis.study.Basics.RegisterBean;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.function.Supplier;

public class Main {
    public static void main(String[] args) {
        var context = new AnnotationConfigApplicationContext(ProjectConfig.class);

        Cat cat = new Cat();
        cat.setName("Barsik");

        Supplier<Cat> supplier = () -> cat;

        context.registerBean("cat", Cat.class, supplier);

        Cat c = context.getBean("cat", Cat.class);
        System.out.println(c.getName());
    }
}
