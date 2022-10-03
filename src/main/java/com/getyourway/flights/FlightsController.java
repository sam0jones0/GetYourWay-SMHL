package com.getyourway.flights;

import com.getyourway.flights.Exception.AirportNotFoundException;
import com.getyourway.flights.localairportdb.InternalAirport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;

import static com.getyourway.Constants.*;

/**
 * REST API Controller for all requests to the internal flightsService.
 *
 * <p>Provides methods to access information on airports, their location and flight departures.
 */
@RestController
@RequestMapping("api/flights")
public class FlightsController {

  @Autowired private FlightsService flightsService;

  /**
   * Queries flightsService for all flight departures from a specified airport to another on a
   * specified date.
   *
   * @param depIcao The ICAO airport code for the departure airport.
   * @param arrIcao The ICAO airport code for the arrival airport.
   * @param depDate ISO 8601 date string. No time needed. E.g. 2022-09-27
   * @return Response entity with FlightDTOs represented as JSON in the body.
   */
  @GetMapping(value = "flightSchedule")
  public ResponseEntity<List<FlightDTO>> getFlightSchedule(
      @RequestParam @Size(min = 4, max = 4, message = "ICAO code must be exactly 4 characters")
          String depIcao,
      @RequestParam @Size(min = 4, max = 4, message = "ICAO code must be exactly 4 characters")
          String arrIcao,
      @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate depDate) {
    List<FlightDTO> response = flightsService.getFlightSchedule(depIcao, arrIcao, depDate);
    return ResponseEntity.status(HttpStatus.OK).body(response);
  }

  /**
   * Queries flightsService for airports closest to the provided lat/lon.
   *
   * <p>Maximum of 10 airports within 100km radius from lat/lon are returned.
   *
   * @param lat Latitude to search from. E.g. 51.5072
   * @param lon Longitude to search from. E.g. -0.127
   * @return Response entity with AirportNearbyResponse represented as JSON in the body. Airports
   *     are sorted by closest -> furthest.
   */
  @GetMapping(value = "nearbyairports")
  public ResponseEntity<AirportResponseDTO> getNearbyAirports(
      @RequestParam float lat, @RequestParam float lon) {

    // Validate lat/lon input
    if (lat < LAT_MIN || lat > LAT_MAX || lon < LON_MIN || lon > LON_MAX) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }

    AirportResponseDTO response = flightsService.getAirportsNearby(lat, lon);
    return ResponseEntity.status(HttpStatus.OK).body(response);
  }

  /**
   * Queries flightsService for airports matching the provided text string.
   *
   * @param searchTerm Airport name or iata/icao code text string to search for. E.g. "Heathrow" or
   *     "LHR".
   * @return Response entity with AirportNearbyResponse (encloses an airport object with addition
   *     full lat/lon information) as JSON in the body.
   */
  @GetMapping(value = "airportsearch")
  public ResponseEntity<List<InternalAirport>> getAirportByText(
      @RequestParam
          @Size(min = 3, max = 30, message = "Search term must be between 3 and 30 characters.")
          String searchTerm) {
    try {
      List<InternalAirport> response = flightsService.getAirportByText(searchTerm);
      return ResponseEntity.status(HttpStatus.OK).body(response);
    } catch (AirportNotFoundException exception) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }
  }
}
