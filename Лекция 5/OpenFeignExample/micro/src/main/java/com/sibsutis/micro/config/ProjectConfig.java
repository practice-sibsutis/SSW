package com.sibsutis.micro.config;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients(basePackages = "com.sibsutis.micro.proxy")
public class ProjectConfig {
}
