package com.getyourway;

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

        //Create Users
        User admin = new User("admin1", "adminpassword1");
        admin.setRoles("ROLE_ADMIN");
        this.repository.save(new User("user1", "password1"));
        this.repository.save(new User("user2", "password2"));
        this.repository.save(admin);
    }

}
