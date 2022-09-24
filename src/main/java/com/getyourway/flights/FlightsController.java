package com.getyourway.flights;

import com.getyourway.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;

@RestController
@RequestMapping("api/flights")
public class FlightsController {

    @Autowired
    private FlightsService flightsService;

    /**
     * Requests flights schedule for all flights from one airport to another on specified day.
     * <p>
     * E.g. Heathrow Airport (IATA: LHR). Madrid Airport (IATA: MAD) on outbound on 1st December 6AM
     * https://airlabs.co/api/v9/schedules?api_key=FLIGHTS_API_KEY&dep_iata=LHR&arr_iata=MAD
     * <p>
     * More information here: https://airlabs.co/docs/schedules
     * <p>
     * FIXME: depTime is ignored by the API as I think it's a premium feature.
     *  Might have to consider only showing
     *
     * @param depIata Departure airport IATA code.
     * @param arrIata Arrival airport IATA code.
     * @param depTime Estimated Departure day as UNIX epoch seconds timestamp.
     * @return TODO returns doc.
     */
    @GetMapping(value = "schedule", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<FlightResponse> getFlightSchedule(
            @RequestParam String depIata,
            @RequestParam String arrIata,
            @RequestParam int depTime) {
        FlightResponse response = flightsService.getFlightsSchedule(depIata, arrIata, depTime);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
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

}
