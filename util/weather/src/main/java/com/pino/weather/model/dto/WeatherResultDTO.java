package com.pino.weather.model.dto;

import lombok.Data;

@Data
public class WeatherResultDTO {
    /**
     * 天氣
     */
    private String weather;
    /**
     * 體感
     */
    private String perceived;
    /**
     * 降雨機率
     */
    private String probabilityOfPrecipitation;
    /**
     * 最低溫度
     */
    private String minTemperature;
    /**
     * 最高溫度
     */
    private String maxTemperature;
}
