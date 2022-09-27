package com.getyourway.repository;

import com.getyourway.trip.Trip;
import com.getyourway.user.User;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;
import java.util.List;

public interface TripRepository extends CrudRepository<Trip, Long> {

    List<Trip> findByUserId(Long userId);

    @Transactional
    void deleteByUserId(long userId); // Deletes all trips for a given userId

}
