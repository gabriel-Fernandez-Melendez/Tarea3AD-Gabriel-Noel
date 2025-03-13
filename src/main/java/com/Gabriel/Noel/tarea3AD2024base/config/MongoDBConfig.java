package com.Gabriel.Noel.tarea3AD2024base.config;



import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(basePackages = "com.Gabriel.Noel.tarea3AD2024base.repositorios.mongo")
public class MongoDBConfig {
}