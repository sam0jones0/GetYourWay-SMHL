package com.getyourway.weather;

import com.getyourway.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import java.time.*;

@RestController
@RequestMapping("/api/weather")
public class WeatherController {

    @Autowired
    private WeatherService weatherService;

//    @GetMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<WeatherResponse> getWeatherLatLong(
//            @RequestParam @DecimalMin(value=Constants.LAT_MIN, message="Latitude cannot be less than -90") @DecimalMax(value=Constants.LAT_MAX, message="Latitude cannot exceed 90") float lat,
//            @RequestParam @DecimalMin(value=Constants.LON_MIN, message="Longitude cannot be less than -180") @DecimalMax(value=Constants.LON_MAX, message="Longitude cannot exceed than 180") float lon) {
//        WeatherResponse response = weatherService.getWeatherByLatLong(lat, lon);
//        return ResponseEntity
//                .status(HttpStatus.OK)
//                .body(response);
//    }

    @GetMapping(value="forecast", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ForecastResponse> getForecastLatLon(
            @RequestParam @DecimalMin(value=Constants.LAT_MIN, message="Latitude cannot be less than -90") @DecimalMax(value=Constants.LAT_MAX, message="Latitude cannot exceed 90") float lat,
            @RequestParam @DecimalMin(value=Constants.LON_MIN, message="Longitude cannot be less than -180") @DecimalMax(value=Constants.LON_MAX, message="Longitude cannot exceed than 180") float lon) {
        var response = weatherService.getForecastByLatLon(lat, lon);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

    @GetMapping(value="historical", consumes=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Flux<String>> getHistoricalForecast(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @RequestParam int numDays,
            @RequestParam @DecimalMin(value=Constants.LAT_MIN, message="Latitude cannot be less than -90") @DecimalMax(value=Constants.LAT_MAX, message="Latitude cannot exceed 90") float lat,
            @RequestParam @DecimalMin(value=Constants.LON_MIN, message="Longitude cannot be less than -180") @DecimalMax(value=Constants.LON_MAX, message="Longitude cannot exceed than 180") float lon) {

        // Calculate each day in epoch seconds, create array for requests
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
