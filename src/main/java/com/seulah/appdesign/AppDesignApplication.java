package com.seulah.appdesign;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.PropertySource;


@SpringBootApplication
@EnableDiscoveryClient
@PropertySource("classpath:config.properties")
public class AppDesignApplication {

    public static void main(String[] args) {
        SpringApplication.run(AppDesignApplication.class, args);
    }

}
