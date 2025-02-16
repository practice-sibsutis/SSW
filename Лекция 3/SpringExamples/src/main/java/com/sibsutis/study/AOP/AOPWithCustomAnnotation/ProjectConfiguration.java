package com.sibsutis.study.AOP.AOPWithCustomAnnotation;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@ComponentScan(basePackages = {"com.sibsutis.study.AOP.AOPWithCustomAnnotation"})
@EnableAspectJAutoProxy
public class ProjectConfiguration {
}
