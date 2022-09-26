package com.getyourway.weather;

import static com.getyourway.Constants.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Size;
import java.time.*;

@RestController
@RequestMapping("api/weather")
public class WeatherController {

    @Autowired
    private WeatherService weatherService;

    /**
     * Returns a weather forecast for the given location
     * @param lat the latitude of the location
     * @param lon the longitude of the location
     * @return ForecastResponse -> a model representing the weather forecast
     */
    @GetMapping(value="forecast", consumes = MediaType.APPLICATION_JSON_VALUE)
        public ResponseEntity<ForecastResponse> getForecastLatLon(
            @RequestParam @DecimalMin(value=LAT_MIN, message=ERR_MSG_LAT) @DecimalMax(value=LAT_MAX, message=ERR_MSG_LAT) float lat,
            @RequestParam @DecimalMin(value=LON_MIN, message=ERR_MSG_LON) @DecimalMax(value=LON_MAX, message=ERR_MSG_LON) float lon) {
                
                var response = weatherService.getForecastByLatLon(lat, lon);
                return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

    /**
     * Returns historical weather data for a given location. The weather data
     * spans over a specified number of days starting at a given date. If the given
     * date is after the current date, return a bad request
     * 
     * @param date the date the weather data should begin
     * @param numDays the number of days of data required
     * @param lat the latitude of the location
     * @param lon the longitude of the location
     * 
     * @return HistoricalWeatherBaseResponse -> a model represending the historical weather
     */
    @GetMapping(
        value="historical", 
        consumes=MediaType.APPLICATION_JSON_VALUE,
        produces=MediaType.APPLICATION_JSON_VALUE
        )
        public ResponseEntity<HistoricalWeatherBaseResponse> getHistoricalForecast(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @RequestParam @Size(min=MIN_FORECAST_DAYS, max=MAX_FORECAST_DAYS, message=ERR_MSG_TIMESPAN) int numDays,
            @RequestParam @DecimalMin(value=LAT_MIN, message=ERR_MSG_LAT) @DecimalMax(value=LAT_MAX, message=ERR_MSG_LAT) float lat,
            @RequestParam @DecimalMin(value=LON_MIN, message=ERR_MSG_LON) @DecimalMax(value=LON_MAX, message=ERR_MSG_LON) float lon) {

                if (date.isAfter(LocalDate.now())) {
                    return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(null);
                }
            
                var days = createDaysArray(date, numDays);
                var response = weatherService.getHistoricalWeather(days, lat, lon);

                return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

    /*
     * Convert date to epoch-seconds for use in 
     * weather API, create an array entry for each day
     */
    private Long[] createDaysArray(LocalDate date, int numDays) {
        var epochSecond = date.toEpochDay() * SECONDS_IN_DAY;
        var days = new Long[numDays];
        for (int i = 0; i < numDays; i++) {
            days[i] = epochSecond;
            epochSecond += SECONDS_IN_DAY;
        }

        return days;
    }
}
