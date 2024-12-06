package com.gerna_v1.banksystem.config;

import com.gerna_v1.banksystem.repositories.TokenRepository;
import com.mongodb.MongoClientSettings;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.util.Collections;

@Configuration
@EnableMongoRepositories(
        basePackages = "com.gerna_v1.banksystem.repositories",
        excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {TokenRepository.class})
)
public class MongoConfig {

    @Value("${spring.data.mongodb.uri:localhost}")
    private String mongoUri;

    @Value("${spring.data.mongodb.database:bankdb}")
    private String database;

    @Bean
    public MongoClient mongoClient() {
        return MongoClients.create(mongoUri);
    }

    @Bean
    public MongoTemplate mongoTemplate() {
        return new MongoTemplate(mongoClient(), database);
    }

    private static final Logger logger = LoggerFactory.getLogger(MongoConfig.class);

    @Bean
    public boolean checkMongoConnection(MongoTemplate mongoTemplate) {
        try {
            mongoTemplate.executeCommand("{ ping: 1 }");
            logger.info("Successfully connected to MongoDB.");
            return true;
        } catch (Exception e) {
            logger.error("Failed to connect to MongoDB.", e);
            return false;
        }
    }
}