package com.pino.restapi.controller;

import com.pino.restapi.model.ContentDTO;
import com.pino.weather.WeatherService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("weather")
public class WeatherController {

    private final WeatherService weatherService;

    @GetMapping
    public ContentDTO getTodayGoogleTrend() {
        return ContentDTO.of(weatherService.getTodayWeather());
    }
}
