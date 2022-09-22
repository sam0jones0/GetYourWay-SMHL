package com.getyourway.weather;

public class WeatherResponse {

    private String weatherMain;
    private String weatherDescription;
    private float mainTemp;

    public WeatherResponse() {}

    public String getWeatherMain() {
        return weatherMain;
    }

    public void setWeatherMain(String weatherMain) {
        this.weatherMain = weatherMain;
    }

    public String getWeatherDescription() {
        return weatherDescription;
    }

    public void setWeatherDescription(String weatherDescription) {
        this.weatherDescription = weatherDescription;
    }

    public float getMainTemp() {
        return mainTemp;
    }

    public void setMainTemp(float mainTemp) {
        this.mainTemp = mainTemp;
    }
}
