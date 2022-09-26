package com.getyourway.weather;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import java.util.Arrays;
import java.util.stream.Collectors;
import static com.getyourway.Constants.*;

@Service
public class WeatherService {

    private final String APIKEY = System.getenv("WEATHER_API_KEY");
    private final WebClient webClient;

    public WeatherService() {
        webClient = WebClient.builder().build();
    }

    public ForecastResponse getForecastByLatLon(float lat, float lon) {
        return webClient
                .get()
                .uri(uriBuilder -> uriBuilder
                        .scheme("https")
                        .host(BASE_HOST)
                        .path(BASE_PATH)
                        .queryParam("lat", lat)
                        .queryParam("lon", lon)
                        .queryParam("exclude", "current,minutely,hourly")
                        .queryParam("units", "metric")
                        .queryParam("cnt", 7)
                        .queryParam("appid", APIKEY)
                        .build())
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(ForecastResponse.class)
                .block();
    }

    public HistoricalWeatherBaseResponse getHistoricalWeather(Long[] days, float lat, float lon) {
        var baseHistory = Arrays.asList(days).stream().map(day -> getHistoricalWeather(day, lat, lon)).collect(Collectors.toList());
        var data = baseHistory.stream().map(x -> x.getData()).collect(Collectors.toList());
        var response = baseHistory.get(0);  // Use first entry as base
        response.setData(data);
        return response;
    }

    private HistoricalWeatherBaseResponse getHistoricalWeather(long epochSecond, float lat, float lon) {
        return webClient
                .get()
                .uri(uriBuilder -> uriBuilder
                        .scheme("https")
                        .host(BASE_HOST)
                        .path(BASE_PATH)
                        .path("/timemachine")
                        .queryParam("lat", lat)
                        .queryParam("lon", lon)
                        .queryParam("dt", epochSecond)
                        .queryParam("units", "metric")
                        .queryParam("appid", APIKEY)
                        .build())
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(HistoricalWeatherBaseResponse.class)
                .block();
    }
}
