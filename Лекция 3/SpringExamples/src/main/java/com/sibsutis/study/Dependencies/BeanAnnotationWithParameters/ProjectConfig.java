package com.sibsutis.study.Dependencies.BeanAnnotationWithParameters;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class ProjectConfig {

    @Bean
    public Cat cat() {
        var cat = new Cat();
        cat.setName("Barsik");
        return cat;
    }

    @Bean
    public Person person(Cat cat) {
        var person = new Person();
        person.setName("Anton");
        person.setCat(cat);
        return person;
    }
}
