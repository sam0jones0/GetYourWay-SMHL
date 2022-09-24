package com.getyourway.weather;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import java.util.Arrays;

@Service
public class WeatherService {

    private final String APIKEY = System.getenv("WEATHER_API_KEY");
    private final WebClient webClient;

    public WeatherService() {
        webClient = WebClient.builder()
                .baseUrl("https://api.openweathermap.org/data/3.0/onecall")
                .build();
    }

//    public WeatherResponse getWeatherByLatLong(float lat, float lon) {
//        return webClient
//                .get()
//                .uri(uriBuilder -> uriBuilder
//                        .queryParam("lat", lat)
//                        .queryParam("lon", lon)
//                        .queryParam("appid", "APIKEY")
//                        .build())
//                .accept(MediaType.APPLICATION_JSON)
//                .retrieve()
//                .bodyToMono(WeatherResponse.class)
//                .block();
//    }

    public ForecastResponse getForecastByLatLon(float lat, float lon) {
        return webClient
                .get()
                .uri(uriBuilder -> uriBuilder
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

    // TODO: Rewrite using history API
    public Flux<String> getHistoricalWeather(Long[] days, float lat, float lon) {
        return Flux.fromIterable(Arrays.asList(days))
                .map(date -> getHistoricalWeather(date, lat, lon));
    }

    private String getHistoricalWeather(Long epochSecond, float lat, float lon) {
        return webClient
                .get()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("lat", lat)
                        .queryParam("lon", lon)
                        .queryParam("dt", epochSecond)
                        .queryParam("units", "metric")
                        .queryParam("appid", APIKEY)
                        .build())
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }
}
