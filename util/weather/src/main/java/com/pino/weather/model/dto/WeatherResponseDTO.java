package com.pino.weather.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class WeatherResponseDTO {
    @JsonProperty("success")
    private String success;
    @JsonProperty("records")
    private RecordDTO recordDTO;
}
