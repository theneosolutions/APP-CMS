package com.seulah.appdesign;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;


@SpringBootApplication
public class AppDesignApplication {

    public static void main(String[] args) {
        SpringApplication.run(AppDesignApplication.class, args);
    }

}
