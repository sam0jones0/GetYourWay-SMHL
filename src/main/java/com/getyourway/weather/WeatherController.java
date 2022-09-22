package com.getyourway.weather;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.getyourway.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import java.time.*;
import java.util.ArrayList;

@RestController
@RequestMapping("/api/weather")
public class WeatherController {

    @Autowired
    private WeatherService weatherService;

    @GetMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WeatherResponse> getWeatherLatLong(@RequestParam float lat, @RequestParam float lon) {
        WeatherResponse response = weatherService.getWeatherByLatLong(lat, lon);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

    @GetMapping("forecast")
    public ResponseEntity<FullForecastResponse> getForecastLatLon(
            @RequestParam @DecimalMin(Constants.LAT_MIN) @DecimalMax(Constants.LAT_MAX) float lat,
            @RequestParam @DecimalMin(Constants.LON_MIN) @DecimalMax(Constants.LON_MAX) float lon) {
        var response = weatherService.getForecastByLatLon(lat, lon);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

    @GetMapping("historical")
    public ResponseEntity<Flux<String>> getHistoricalForecast(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @RequestParam int numDays,
            @RequestParam @DecimalMin(Constants.LAT_MIN) @DecimalMax(Constants.LAT_MAX) float lat,
            @RequestParam @DecimalMin(Constants.LON_MIN) @DecimalMax(Constants.LON_MAX) float lon) {
        var epochSecond = date.toEpochDay() * Constants.SECONDS_IN_DAY;
        var days = new Long[numDays];
        for (int i = 0; i < days.length; i++) {
            days[i] = epochSecond;
            epochSecond += Constants.SECONDS_IN_DAY;
        }

        var response = weatherService.getHistoricalWeather(days, lat, lon);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);

    }
}
