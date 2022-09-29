package com.getyourway;

import org.aspectj.apache.bcel.util.ClassPath;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.data.repository.init.Jackson2RepositoryPopulatorFactoryBean;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

@SpringBootApplication
@EnableGlobalMethodSecurity(
        prePostEnabled = true,
        securedEnabled = true
)
public class GetyourwayApplication {

    public static void main(String[] args) {
        SpringApplication.run(GetyourwayApplication.class, args);
    }

    @Bean
    public Jackson2RepositoryPopulatorFactoryBean getRespositoryPopulator() {
        Jackson2RepositoryPopulatorFactoryBean factory = new Jackson2RepositoryPopulatorFactoryBean();
        factory.setResources(
                new Resource[] {new ClassPathResource("allAirports.json")});
        System.out.println(ClassPath.getClassPath());
        return factory;
    }
}
