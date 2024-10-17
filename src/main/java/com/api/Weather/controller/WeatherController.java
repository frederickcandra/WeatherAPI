package com.api.Weather.controller;

import com.api.Weather.model.WeatherModel;
import com.api.Weather.service.WeatherService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/weather")
public class WeatherController {

    private final WeatherService weatherService;

    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @GetMapping("/current")
    public ResponseEntity<WeatherModel> getCurrentWeather() {
        WeatherModel weather = weatherService.GetWeather();
        if (weather != null) {
            return new ResponseEntity<>(weather, HttpStatus.OK);
        } else {
            WeatherModel notFoundWeather = new WeatherModel("Tangerang", "Data tidak ditemukan", "N/A", "N/A","N/A");
            return new ResponseEntity<>(notFoundWeather, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/data")
    public ResponseEntity<WeatherModel> getWeatherData() {
        WeatherModel weather = weatherService.getWeatherFromRedis();
        if (weather != null) {
            return new ResponseEntity<>(weather, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

}
