package com.getyourway.flights;

import com.getyourway.Constants;
import org.hibernate.cfg.NotYetImplementedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Size;
import java.time.LocalDate;

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
   * Queries flightsService for all flight departures from a specified airport on a specified date.
   *
   * @param depIcao The ICAO airport code for the departure airport.
   * @param date ISO 8601 date string. No time needed. E.g. 2022-09-27
   * @return Response entity with AirportScheduleResponse represented as JSON in the body.
   */
  @GetMapping(value = "airportSchedule", consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<AirportScheduleResponseDTO> getAirportSchedule(
      @RequestParam String depIcao,
      @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
    AirportScheduleResponseDTO response = flightsService.getAirportSchedule(depIcao, date);
    return ResponseEntity.status(HttpStatus.OK).body(response);
  }

  /**
   * TODO // Not Yet Implemented
   *
   * @param depIata
   * @param arrIata
   * @param depTime
   * @return
   */
  @GetMapping(value = "flightSchedule", consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<FlightResponseDTO> getFlightSchedule(
      @RequestParam String depIata, @RequestParam String arrIata, @RequestParam int depTime) {
    // TODO: Using getAirportSchedule - return only flights to one specific destination.
    throw new NotYetImplementedException();
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
  @GetMapping(value = "nearbyairports", consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<AirportResponseDTO> getNearbyAirports(
      @RequestParam
          @DecimalMin(value = Constants.LAT_MIN, message = "Latitude cannot be less than -90")
          @DecimalMax(value = Constants.LAT_MAX, message = "Latitude cannot exceed 90")
          float lat,
      @RequestParam
          @DecimalMin(value = Constants.LON_MIN, message = "Longitude cannot be less than -180")
          @DecimalMax(value = Constants.LON_MAX, message = "Longitude cannot exceed than 180")
          float lon) {
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
  @GetMapping(value = "airportsearch", consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<AirportResponseDTO> getAirportByText(
      @RequestParam
          @Size(min = 3, max = 30, message = "Search term must be between 3 and 30 characters.")
          String searchTerm) {
    AirportResponseDTO response = flightsService.getAirportByText(searchTerm);
    return ResponseEntity.status(HttpStatus.OK).body(response);
  }
}
