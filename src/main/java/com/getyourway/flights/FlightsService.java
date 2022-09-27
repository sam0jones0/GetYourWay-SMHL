package com.getyourway.flights;

import org.hibernate.cfg.NotYetImplementedException;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
public class FlightsService {

  private final String APIKEY = System.getenv("FLIGHTS_API_KEY");
  private final WebClient webClient;

  public FlightsService() {

    this.webClient =
        WebClient.builder()
            .baseUrl("https://aerodatabox.p.rapidapi.com")
            .defaultHeaders(
                httpHeaders -> {
                  httpHeaders.set("X-RapidAPI-Key", APIKEY);
                  httpHeaders.set("X-RapidAPI-Host", "aerodatabox.p.rapidapi.com");
                })
            .build();
  }

  public AirportScheduleResponseDTO getAirportSchedule(String depIcao, LocalDate date) {
    // Call to external schedule API requires max 12-hours period.
    // As most flights are between 08-20hrs we only concern ourselves with these.
    String fromIsoDate;
    String toIsoDate;
    if (date.isEqual(LocalDate.now())) {
      // We are looking for flights in the next 12 hours
      LocalDateTime fromDateTime = LocalDateTime.now();
      toIsoDate = fromDateTime.plusHours(12).toString();
      fromIsoDate = fromDateTime.toString();
    } else {
      // We are looking for flights in the future.
      fromIsoDate = date.atTime(8, 0).toString();
      toIsoDate = date.atTime(20, 0).toString();
    }

    return webClient
        .get()
        .uri(
            uriBuilder ->
                uriBuilder
                    .path(
                        "flights/airports/icao/"
                            + depIcao
                            + '/'
                            + fromIsoDate
                            + '/'
                            + toIsoDate
                            + '/')
                    .queryParam("withLeg", true)
                    .queryParam("direction", "Departure")
                    .queryParam("withCancelled", "false")
                    .queryParam("withCodeShared", "false")
                    .queryParam("withCargo", "false")
                    .queryParam("withPrivate", "false")
                    .queryParam("withLocation", "false")
                    .build())
        .accept(MediaType.APPLICATION_JSON)
        .retrieve()
        .bodyToMono(AirportScheduleResponseDTO.class)
        .block();
  }

  public FlightResponseDTO getFlightSchedule(String depIcao, String arrIcao, LocalDate date) {
    // TODO: Using getAirportSchedule - return only flights to one specific destination.
    //  Make DB indexing all airports in world (10MB json file shouldnt be too big hopefully).
    //  Try DB access with match icao | iata | shortname
    //    else make call to getAirportByText API.
    //      else return not found status code etc.
    throw new NotYetImplementedException();
  }

  public AirportResponseDTO getAirportsNearby(float lat, float lon) {
    return webClient
        .get()
        .uri(
            uriBuilder ->
                uriBuilder
                    .path(
                        "airports/search/location/"
                            + lat
                            + '/'
                            + lon
                            + '/'
                            + "km/100/10") // 100km radius, return max 10 airports.
                    .queryParam("withFlightInfoOnly", "true")
                    .build())
        .accept(MediaType.APPLICATION_JSON)
        .retrieve()
        .bodyToMono(AirportResponseDTO.class)
        .block();
  }

  public AirportResponseDTO getAirportByText(String searchTerm) {
    return webClient
        .get()
        .uri(
            uriBuilder ->
                uriBuilder
                    .path("/airports/search/term")
                    .queryParam("q", searchTerm)
                    .queryParam("limit", 10)
                    .queryParam("withFlightInfoOnly", "true")
                    .build())
        .accept(MediaType.APPLICATION_JSON)
        .retrieve()
        .bodyToMono(AirportResponseDTO.class)
        .block();
  }
}
