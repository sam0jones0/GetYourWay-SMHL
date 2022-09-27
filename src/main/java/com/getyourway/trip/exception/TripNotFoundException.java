package com.getyourway.trip.exception;

public class TripNotFoundException extends RuntimeException{

    public TripNotFoundException(Long id) {
        super("Could not find trip " + id);
    }

}
