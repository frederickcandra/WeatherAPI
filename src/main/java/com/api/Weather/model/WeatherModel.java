package com.api.Weather.model;

import lombok.Data;

@Data
public class WeatherModel {

    private String cityName;
    private String description;
    private String humidity;
    private String temp;
    private String dateTime;

    public WeatherModel(String cityName, String description, String humidity, String temp, String dateTime) {
        this.cityName = cityName;
        this.description = description;
        this.humidity = humidity;
        this.temp = temp;
        this.dateTime = dateTime;
    }

    public WeatherModel() {

    }
}
