package com.getyourway.weather;

import static com.getyourway.Constants.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
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
    @GetMapping(value="forecast")
        public ResponseEntity<ForecastResponseDTO> getForecastLatLon(
            @RequestParam @DecimalMin(value=LAT_MIN, message=ERR_MSG_LAT) @DecimalMax(value=LAT_MAX, message=ERR_MSG_LAT) float lat,
            @RequestParam @DecimalMin(value=LON_MIN, message=ERR_MSG_LON) @DecimalMax(value=LON_MAX, message=ERR_MSG_LON) float lon) {
                
                var response = weatherService.getForecastByLatLon(lat, lon);
                for (Daily d : response.daily) { 
                    d.date = LocalDate.ofEpochDay(d.dt / SECONDS_IN_DAY);
                }
                return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

    /**
     * Returns historical weather data for a given location. The weather data
     * spans over 7 days starting at a given date. If the given date is after 
     * the current date, return a bad request
     * 
     * @param date the date the weather data should begin
     * @param lat the latitude of the location
     * @param lon the longitude of the location
     * 
     * @return HistoricalWeatherBaseResponse -> a model represending the historical weather
     */
    @GetMapping(value="historical")
        public ResponseEntity<HistoricalWeatherResponseDTO> getHistoricalForecast(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @RequestParam @DecimalMin(value=LAT_MIN, message=ERR_MSG_LAT) @DecimalMax(value=LAT_MAX, message=ERR_MSG_LAT) float lat,
            @RequestParam @DecimalMin(value=LON_MIN, message=ERR_MSG_LON) @DecimalMax(value=LON_MAX, message=ERR_MSG_LON) float lon) {

                if (date.isAfter(LocalDate.now())) {
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
     * Convert date to epoch-seconds for use in 
     * weather API, create an array entry for each day
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
