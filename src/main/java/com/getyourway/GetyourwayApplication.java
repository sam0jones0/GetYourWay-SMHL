package com.getyourway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
public class GetyourwayApplication {

    public static void main(String[] args) {
        SpringApplication.run(GetyourwayApplication.class, args);
    }

}
