package com.sibsutis.study.Dependencies.QualifierAnnotation;

import org.springframework.beans.factory.annotation.Qualifier;
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
    public Cat cat2() {
        var cat = new Cat();
        cat.setName("Vaska");
        return cat;
    }

    @Bean
    public Person person(
            @Qualifier("cat2") Cat cat) {
        var person = new Person();
        person.setName("Anton");
        person.setCat(cat);
        return person;
    }
}
