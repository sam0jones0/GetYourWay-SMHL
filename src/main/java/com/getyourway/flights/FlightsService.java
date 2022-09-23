package com.getyourway.flights;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class FlightsService {

    private final String APIKEY = System.getenv("FLIGHTS_API_KEY");
    private final WebClient webClient;

    public FlightsService() {
        this.webClient = WebClient.builder()
                .baseUrl("https://airlabs.co/api/v9/schedules")
                .build();
    }

    public FlightResponse getFlightsSchedule(String depIata, String arrIata, int depTime) {
        // NOT IMPLEMENTED
        return null;
    }
}
