package com.getyourway.trip;

import com.getyourway.repository.TripRepository;
import com.getyourway.repository.UserRepository;
import com.getyourway.trip.exception.TripNotFoundException;
import com.getyourway.user.Exception.UserNotFoundException;
import com.getyourway.user.User;
import com.getyourway.user.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TripService {

    private final TripRepository tripRepository;

    private final UserService userService;

    public TripService(TripRepository tripRepository, UserService userService) {
        this.tripRepository = tripRepository;
        this.userService = userService;
    }

    // Create

    public Trip save(long userId, Trip trip) {

        User user = userService.findById(userId);
        trip.setUser(user);
        return tripRepository.save(trip);
    }

    // Read

    public List<Trip> getTripsByUserId(long userId) {

        if (!userService.exitsById(userId)) {
            throw new UserNotFoundException(userId);
        }
        return tripRepository.findByUserId(userId);
    }

    public Trip getTrip(long id) {
        return tripRepository.findById(id)
                .orElseThrow(() -> new TripNotFoundException(id));
    }

    // Delete

    public void deleteTrip(long id) {
        tripRepository.deleteById(id);
    }

    public void deleteByUserId(long userId) {

        if (!userService.exitsById(userId)) {
            throw new UserNotFoundException(userId);
        }

        tripRepository.deleteByUserId(userId);

    }
}
