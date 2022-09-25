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

@RestController
@RequestMapping("api/flights")
public class FlightsController {

    @Autowired
    private FlightsService flightsService;

    /**
     * Requests flights schedule for all flights from one airport to another on specified day.
     *
     * @param depIcao Departure airport ICAO code.
     * @param date Estimated Departure day as ISO-8601 date.
     * @return TODO returns doc.
     */
    @GetMapping(value = "airportSchedule", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AirportScheduleResponse> getAirportSchedule(
            @RequestParam String depIcao,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        AirportScheduleResponse response = flightsService.getAirportSchedule(depIcao, date);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

    @GetMapping(value = "flightSchedule", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<FlightResponse> getFlightSchedule(
            @RequestParam String depIata,
            @RequestParam String arrIata,
            @RequestParam int depTime) {
        // TODO: Using getAirportSchedule - return only flights to one specific destination.
        throw new NotYetImplementedException();
    }

    @GetMapping(value = "nearbyairports", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AirportNearbyResponse> getNearbyAirports(
            @RequestParam @DecimalMin(value = Constants.LAT_MIN, message = "Latitude cannot be less than -90") @DecimalMax(value = Constants.LAT_MAX, message = "Latitude cannot exceed 90") float lat,
            @RequestParam @DecimalMin(value = Constants.LON_MIN, message = "Longitude cannot be less than -180") @DecimalMax(value = Constants.LON_MAX, message = "Longitude cannot exceed than 180") float lon) {
        AirportNearbyResponse response = flightsService.getAirportsNearby(lat, lon);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

    @GetMapping(value = "airportsearch", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AirportNearbyResponse> getAirportByText(@RequestParam @Size(min = 3, max = 30, message = "Search term must be between 3 and 30 characters.") String searchTerm) {
        AirportNearbyResponse response = flightsService.getAirportByText(searchTerm);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}
