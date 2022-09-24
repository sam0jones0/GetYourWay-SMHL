package com.getyourway.weather;

import java.util.ArrayList;

public class ForecastResponse {

    public int lat;
    public int lon;
    public String timezone;
    public int timezone_offset;
    public ArrayList<Daily> daily;
}

class Daily {
    public int dt;
    public int sunrise;
    public int sunset;
    public int moonrise;
    public int moonset;
    public Temp temp;
    public FeelsLike feels_like;
    public int pressure;
    public int humidity;
    public double wind_speed;
    public ArrayList<Weather> weather;
    public int clouds;
    public double pop;
    public double uvi;
}

 class FeelsLike {
    public double day;
    public double night;
    public double eve;
    public double morn;
}

 class Temp {
    public double day;
    public double min;
    public double max;
    public double night;
    public double eve;
    public double morn;
}

class Weather {
    public int id;
    public String main;
    public String description;
    public String icon;
}
