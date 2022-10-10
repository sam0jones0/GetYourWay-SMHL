package com.getyourway;

import com.getyourway.repository.TripRepository;
import com.getyourway.trip.Trip;
import com.getyourway.user.User;
import com.getyourway.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.time.Month;

@Configuration
public class DatabaseLoader implements CommandLineRunner {

    private final UserRepository userRepository;
    private final TripRepository tripRepository;

    @Autowired
    public DatabaseLoader(UserRepository userRepository, TripRepository tripRepository) {
        this.userRepository = userRepository;
        this.tripRepository = tripRepository;
    }

    /**
     * Callback used to run. Used for the purpose of pre-populating
     * a database
     *
     * @param strings incoming main method arguments
     * @throws Exception
     */
    @Override
    public void run(String... strings) throws Exception {

        // Create Users
        User admin = new User("admin1", "adminpassword1");
        admin.setRoles("ROLE_ADMIN");
        User user1 = new User("user1", "password1");
        User user2 = new User("user2", "password2");
        this.userRepository.save(user1);
        this.userRepository.save(user2);
        this.userRepository.save(admin);

        // Create Trips
        Trip trip1 = new Trip("Flying home",
                "London Gatwick",
                "London Heathrow",
                LocalDateTime.of(2023, Month.JULY,15,10,02,00)
             );
        trip1.setUser(user1);
//
        tripRepository.save(trip1);


        // Create Trips
        Trip trip2 = new Trip("Summer Holidays",
                "London Heathrow",
                "Dubrovnik airport",
                LocalDateTime.of(2023, Month.JULY,17,18,32,00)
        );
        trip2.setUser(user1);
//
        tripRepository.save(trip2);


        Trip trip3 = new Trip("Back to work",
                "Dubrovnik airport",
                "Edinburgh airport",
                LocalDateTime.of(2023, Month.JULY,31,06,21,00)
        );
        trip3.setUser(user1);
//
        tripRepository.save(trip3);
    }

}
