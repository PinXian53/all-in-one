package com.pino.weather.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class LocationDTO {
    @JsonProperty("locationName")
    private String locationName;
    @JsonProperty("weatherElement")
    private List<WeatherElementDTO> weatherElementDTOList;
}
