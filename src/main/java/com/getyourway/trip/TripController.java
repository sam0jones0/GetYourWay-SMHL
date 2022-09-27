package com.getyourway.trip;

import com.getyourway.Constants;
import com.getyourway.user.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class TripController {

    public TripService tripService;

    public UserService userService;

    public TripController(TripService tripService, UserService userService) {
        this.tripService = tripService;
        this.userService = userService;
    }

    // POST

    @PostMapping("/users/{userId}/trips")
    @PreAuthorize("@userService.isCurrentUserOrAdmin(principal.getUsername(), #userId)")
    public ResponseEntity<Trip> createTrip(@PathVariable(value = "userId") long userId, @RequestBody Trip trip) {

        Trip response = tripService.save(userId, trip);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);

    }

    // GET

    @GetMapping("/users/{userId}/trips")
    @PreAuthorize("@userService.isCurrentUserOrAdmin(principal.getUsername(), #userId)")
    public ResponseEntity<List<Trip>> getTripsByUserId(@PathVariable long userId) {

        List<Trip> trips = tripService.getTripsByUserId(userId);

        return ResponseEntity
                .ok(trips);
    }

    @GetMapping("/trips/{id}")
    @Secured(Constants.USER)
    public ResponseEntity<Trip> getTrip(@PathVariable long id) {

        Trip trip = tripService.getTrip(id);

        return ResponseEntity
                .ok(trip);
    }

    // DELETE

    //TODO: 'delete' and 'deleteByUserId' (batch delete)
}
