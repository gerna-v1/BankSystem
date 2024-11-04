package com.gerna_v1.banksystem.config;

import com.gerna_v1.banksystem.repositories.SessionRepository;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(
        basePackages = "com.gerna_v1.banksystem.repositories",
        excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {SessionRepository.class})
)
public class MongoConfig {
}