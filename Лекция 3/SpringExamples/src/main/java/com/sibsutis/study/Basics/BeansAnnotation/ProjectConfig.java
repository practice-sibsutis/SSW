package com.sibsutis.study.Basics.BeansAnnotation;

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

    @Primary
    @Bean
    public Cat cat2() {
        var cat = new Cat();
        cat.setName("Kuzya");
        return cat;
    }

    @Bean
    public Cat cat3() {
        var cat = new Cat();
        cat.setName("Vaska");
        return cat;
    }
}
