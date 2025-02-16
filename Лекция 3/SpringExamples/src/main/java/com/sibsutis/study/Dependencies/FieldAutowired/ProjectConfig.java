package com.sibsutis.study.Dependencies.FieldAutowired;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProjectConfig {

    @Bean
    public Cat cat() {
        var cat = new Cat();
        cat.setName("Barsik");
        return cat;
    }

    @Bean
    public Person person() {
        var person = new Person();
        person.setName("Anton");
        return person;
    }
}
