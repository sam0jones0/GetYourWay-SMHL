package com.getyourway.database;

import com.getyourway.user.User;
import com.getyourway.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DatabaseLoader implements CommandLineRunner {

    private final UserRepository repository;

    @Autowired
    public DatabaseLoader(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public void run(String... strings) throws Exception {
        this.repository.save(new User("user1", "password1"));
        this.repository.save(new User("user2", "password2"));
    }

}
