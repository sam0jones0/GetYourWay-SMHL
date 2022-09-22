package com.getyourway.weather;

import java.util.ArrayList;

public class FullForecastResponse {

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
    public double moon_phase;
    public Temp temp;
    public FeelsLike feels_like;
    public int pressure;
    public int humidity;
    public double dew_point;
    public double wind_speed;
    public int wind_deg;
    public double wind_gust;
    public ArrayList<Weather> weather;
    public int clouds;
    public double pop;
    public double rain;
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
