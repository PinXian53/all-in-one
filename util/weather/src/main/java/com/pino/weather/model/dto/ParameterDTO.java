package com.pino.weather.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ParameterDTO {
    @JsonProperty("parameterName")
    private String parameterName;
    @JsonProperty("parameterValue")
    private String parameterValue;
}
