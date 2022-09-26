package com.getyourway.weather;

import java.util.List;

public class HistoricalWeatherBaseResponse {
    public int lat;
    public int lon;
    public String timezone;
    public int timezone_offset;
    public List<Data> data;
    
    public Data getData() {
        return data.get(0);
    }

    public void setData(List<Data> data) {
        this.data = data;
    }
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


