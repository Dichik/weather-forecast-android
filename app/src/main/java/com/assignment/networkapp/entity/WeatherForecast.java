package com.assignment.networkapp.entity;

public class WeatherForecast {
    private final String cityName;
    private final double temperature;

    public WeatherForecast(String cityName, double temperature) {
        this.cityName = cityName;
        this.temperature = temperature;
    }

    public String getCityName() {
        return cityName;
    }

    public double getTemperature() {
        return temperature;
    }
}
