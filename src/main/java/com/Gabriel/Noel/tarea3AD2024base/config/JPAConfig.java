package com.Gabriel.Noel.tarea3AD2024base.config;



import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = "com.Gabriel.Noel.tarea3AD2024base.repositorios.jpa")
public class JPAConfig {
}