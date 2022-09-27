package com.getyourway.weather;

import java.util.List;

public class HistoricalWeatherDto {
    public int lat;
    public int lon;
    public String timezone;
    public int timezone_offset;
    public List<Data> data;
}

class Data {
    public int dt;
    public int sunrise;
    public int sunset;
    public double temp;
    public double feels_like;
    public int pressure;
    public int humidity;
    public double dew_point;
    public int clouds;
    public double wind_speed;
    public int wind_deg;
    public List<Weather> weather;
}


