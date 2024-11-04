package com.gerna_v1.banksystem.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class BeanLogger {

    @Bean
    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
        return args -> {
            String[] beanNames = ctx.getBeanDefinitionNames();
            Arrays.sort(beanNames);
            System.out.println("Beans initialized by Spring Boot (filtered by package 'com.gerna_v1.banksystem'):");
            for (String beanName : beanNames) {
                Object bean = ctx.getBean(beanName);
                if (bean.getClass().getPackageName().startsWith("com.gerna_v1.banksystem")) {
                    System.out.println("Se ha inicializado:  " + beanName);
                }
            }
        };
    }
}