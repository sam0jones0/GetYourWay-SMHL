package com.getyourway.flights;

import org.hibernate.cfg.NotYetImplementedException;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class FlightsService {

    private final String APIKEY = System.getenv("FLIGHTS_API_KEY");
    private final WebClient webClient;

    public FlightsService() {

        this.webClient = WebClient.builder()
                .baseUrl("https://aerodatabox.p.rapidapi.com")
                .defaultHeaders(httpHeaders -> {
                    httpHeaders.set("X-RapidAPI-Key", APIKEY);
                    httpHeaders.set("X-RapidAPI-Host", "aerodatabox.p.rapidapi.com");
                })
                .build();
    }

    public FlightResponse getFlightsSchedule(String depIata, String arrIata, int depTime) {
        // NOT IMPLEMENTED
        throw new NotYetImplementedException();
    }

    public AirportNearbyResponse getAirportsNearby(float lat, float lon) {
        return webClient
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path("airports/search/location/")
                        .path(String.valueOf(lat) + '/')
                        .path(String.valueOf(lon) + '/')
                        .path("km/100/10")  // 100km radius, return max 10 airports.
                        .queryParam("withFlightInfoOnly", "true")
                        .build())
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(AirportNearbyResponse.class)
                .block();
    }


    public AirportNearbyResponse getAirportByText(String searchTerm) {
        return webClient
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path("/airports/search/term")
                        .queryParam("q", searchTerm)
                        .queryParam("limit", 10)
                        .queryParam("withFlightInfoOnly", "true")
                        .build())
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(AirportNearbyResponse.class)
                .block();
    }
}
