package com.getyourway.flights;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
}
