package com.getyourway.flights;

import com.getyourway.flights.Exception.AirportNotFoundException;
import com.getyourway.flights.localairportdb.InternalAirport;
import com.getyourway.flights.localairportdb.InternalAirportRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/** Internal service for accessing airport/flight information from external APIs and internal database resources
 * */
@Service
public class FlightsService {

  private final String APIKEY = System.getenv("FLIGHTS_API_KEY");

  private final WebClient webClient;
  @Autowired private InternalAirportRepo internalAirportRepo;

  public FlightsService() {

    this.webClient =
        WebClient.builder()
            .codecs(
                clientCodecConfigurer ->
                    clientCodecConfigurer.defaultCodecs().maxInMemorySize(1000000))
            .baseUrl("https://aerodatabox.p.rapidapi.com")
            .defaultHeaders(
                httpHeaders -> {
                  httpHeaders.set("X-RapidAPI-Key", APIKEY);
                  httpHeaders.set("X-RapidAPI-Host", "aerodatabox.p.rapidapi.com");
                })
            .build();
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

  public List<InternalAirport> getAirportByText(String searchTerm) throws AirportNotFoundException {
    return this.internalAirportRepo
        .getByUserString(searchTerm)
        .orElseThrow(AirportNotFoundException::new);
  }

  private AirportScheduleResponseDTO getAirportSchedule(String depIcao, LocalDate date) {
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

  public List<FlightDTO> getFlightSchedule(String depIcao, String arrIcao, LocalDate date) {
    InternalAirport arrAirportInternal =
        this.internalAirportRepo
            .getAirportByIcao(arrIcao)
            .orElseThrow(AirportNotFoundException::new)
            .get(0);

    AirportScheduleResponseDTO airportSchedule = this.getAirportSchedule(depIcao, date);
    List<FlightDTO> parsedFlights = new ArrayList<>();

    for (FlightDTO flight : airportSchedule.departures) {
      if (Objects.equals(flight.arrival.airport.icao, arrAirportInternal.getIcao())
          || Objects.equals(flight.arrival.airport.iata, arrAirportInternal.getIata())) {
        // We already have the airport code. Set Names/Codes correctly.
        flight.arrival.airport.name = arrAirportInternal.getName();
        flight.arrival.airport.city = arrAirportInternal.getCity();
        flight.arrival.airport.icao = arrAirportInternal.getIcao();
        flight.arrival.airport.iata = arrAirportInternal.getIata();
        parsedFlights.add(flight);
      } else {
        // Without airport code, we have to search our internal DB to identify airport.
        String unidentifiedAirportName;
        if (flight.arrival.airport.name != null) {
          unidentifiedAirportName = flight.arrival.airport.name;
        } else if (flight.arrival.airport.shortName != null) {
          unidentifiedAirportName = flight.arrival.airport.shortName;
        } else if (flight.arrival.airport.municipalityName != null) {
          unidentifiedAirportName = flight.arrival.airport.municipalityName;
        } else continue;

        // unidentifiedAirportName is now not null.
        if (arrAirportInternal.getCity().contains(unidentifiedAirportName)
            || arrAirportInternal.getName().contains(unidentifiedAirportName)) {
          flight.arrival.airport.name = arrAirportInternal.getName();
          flight.arrival.airport.city = arrAirportInternal.getCity();
          flight.arrival.airport.icao = arrAirportInternal.getIcao();
          flight.arrival.airport.iata = arrAirportInternal.getIata();
          parsedFlights.add(flight);
        }
      }
    }
    return parsedFlights;
  }
}
