package com.getyourway.weather;

import com.fasterxml.jackson.databind.util.JSONPObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;

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
    public ResponseEntity<FullForecastResponse> getForecastLatLon(@RequestParam float lat, @RequestParam float lon) {
        var response = weatherService.getForecastByLatLon(lat, lon);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

}
