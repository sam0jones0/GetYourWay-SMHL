package com.getyourway.weather;

import static com.getyourway.Constants.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.*;

@CrossOrigin
@RestController
@RequestMapping("api/weather")
public class WeatherController {

    @Autowired
    private WeatherService weatherService;

    /**
     * Returns a weather forecast for the given location by latitude and longitude
     * 
     * @param lat the latitude of the location
     * @param lon the longitude of the location
     * 
     * @return ForecastResponse -> a model representing the weather forecast
     */
    @GetMapping(value="forecast")
        public ResponseEntity<ForecastResponseDTO> getForecastLatLon(
            @RequestParam float lat,
            @RequestParam float lon) {
                
                // Validate lat/lon input
                if (lat < LAT_MIN || lat > LAT_MAX || 
                    lon < LON_MIN || lon > LON_MAX) {
                    return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(null);
                }

                var response = weatherService.getForecastByLatLon(lat, lon);
                for (Daily d : response.daily) { 
                    d.date = LocalDate.ofEpochDay(d.dt / SECONDS_IN_DAY);
                }
                return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

    /**
     * Returns historical weather data for the given location by latitude and 
     * longitude. The data spans over 7 days starting at the provided date. 
     * If the given date is after the current date, return a BadRequest
     * 
     * @param date the date the weather data should begin
     * @param lat the latitude of the location
     * @param lon the longitude of the location
     * 
     * @return HistoricalWeatherBaseResponse -> a model represending the historical weather
     */
    @GetMapping("historical")
        public ResponseEntity<HistoricalWeatherResponseDTO> getHistoricalForecast(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @RequestParam float lat,
            @RequestParam float lon) {
                
                // Validate date input
                if (date.isAfter(LocalDate.now())) {
                    return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(null);
                }

                // Validate lat/lon input
                if (lat < LAT_MIN || lat > LAT_MAX || lon < LON_MIN || lon > LON_MAX) {
                    return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(null);
                }
            
                var days = createDaysArray(date);
                var response = weatherService.getHistoricalWeather(days, lat, lon);
                for (Data d : response.data) { 
                    d.date = LocalDate.ofEpochDay(d.dt / SECONDS_IN_DAY);
                }

                return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

    /*
     * Convert date to epoch-seconds for use in weather API, create an array entry for each day
     */
    private Long[] createDaysArray(LocalDate date) {
        var epochSecond = date.toEpochDay() * SECONDS_IN_DAY;
        var days = new Long[DAYS_IN_WEEK];
        for (int i = 0; i < DAYS_IN_WEEK; i++) {
            days[i] = epochSecond;
            epochSecond += SECONDS_IN_DAY;
        }

        return days;
    }
}
