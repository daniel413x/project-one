package com.warehouse.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import com.warehouse.utils.DatabaseUtil;

@Configuration
public class SeedDatabase {

    @Autowired
    public SeedDatabase() {
    }

    // clear datasbase on server startup
    // @Bean
    // @Order(1)
    // CommandLineRunner clearDatabase(DatabaseUtil databaseUtil) {
    //   return args -> {
    //       databaseUtil.clearDatabase();
    //   };
    // }

    // seed database on server startup
    @Bean
    @Order(2)
    CommandLineRunner seedTheDatabase(DatabaseUtil databaseUtil) {
        return args -> {
            databaseUtil.seedTheDatabase();
        };
    }
}