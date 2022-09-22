package com.getyourway.weather;

public class FullWeatherResponse {

    public Coord coord;
    public Weather[] weather;
    public String base;
    public Main main;
    public int visibility;
    public Wind wind;
    public Clouds clouds;
    public int dt;
    public Sys sys;
    public int id;
    public int cod;

    public Main getMain() {
        return this.main;
    }

}
class Coord {
    public float lon;
    public float lat;
}

class Main {
    public float temp;
    public int pressure;
    public int humidity;
    public float temp_min;
    public float temp_max;

    public float getTemp() {
        return this.temp;
    }
}

class Wind {
    public float speed;
    public int deg;
}

class Clouds {
    public int all;
}

class Sys {
    public int type;
    public int id;
    public float message;
    public String country;
    public int sunrise;
    public int sunset;
}

class Weather {
    public int id;
    public String main;
    public String description;
    public String icon;

}


