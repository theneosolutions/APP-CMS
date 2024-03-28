package com.seulah.appdesign;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.PropertySource;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.config.EnableIntegration;


@SpringBootApplication
@EnableDiscoveryClient
@PropertySource("classpath:config.properties")
@IntegrationComponentScan
@EnableIntegration
public class AppDesignApplication {

    public static void main(String[] args) {
        SpringApplication.run(AppDesignApplication.class, args);
    }

}
