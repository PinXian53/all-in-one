package com.pino.weather.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class WeatherElementDTO {
    @JsonProperty("elementName")
    private String elementName;
    @JsonProperty("time")
    private List<TimeDTO> timeDTOList;
}
