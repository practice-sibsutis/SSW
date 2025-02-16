package com.sibsutis.study.AOP.SimpleExample;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@ComponentScan(basePackages = {"com.sibsutis.study.AOP.SimpleExample"})
@EnableAspectJAutoProxy
public class ProjectConfiguration {
}
