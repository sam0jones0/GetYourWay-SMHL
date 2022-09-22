package com.getyourway.weather;

import com.fasterxml.jackson.databind.util.JSONPObject;
import org.apache.tomcat.util.json.JSONParser;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class WeatherService {

    private final String APIKEY = "PLACE YOUR API KEY HERE";
    private final WebClient webClient;

    public WeatherService() {
        webClient = WebClient.builder()
                .baseUrl("https://api.openweathermap.org/data/2.5/weather")
                .build();
    }
    public String getWeatherByLatLong(float lat, float lon) {

        return webClient
                .get()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("lat", lat)
                        .queryParam("lon", lon)
                        .queryParam("appid", APIKEY)
                        .build())
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(String.class)
                .block();

    }

}