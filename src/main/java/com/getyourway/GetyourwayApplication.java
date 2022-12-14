package com.getyourway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.data.repository.init.Jackson2RepositoryPopulatorFactoryBean;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

@SpringBootApplication
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class GetyourwayApplication {

  public static void main(String[] args) {
    SpringApplication.run(GetyourwayApplication.class, args);
  }

  /**
   * Populates the production database `airports` table with data from the allAirports.json
   * resource.
   *
   * @return A JSON to repository factory bean.
   */
  @Profile({"prod"})
  @Bean
  public Jackson2RepositoryPopulatorFactoryBean getRespositoryPopulator() {
    Jackson2RepositoryPopulatorFactoryBean factory = new Jackson2RepositoryPopulatorFactoryBean();
    factory.setResources(new Resource[] {new ClassPathResource("allAirports.json")});
    return factory;
  }
}
