package com.pino.weather.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class RecordDTO {
    @JsonProperty("datasetDescription")
    private String datasetDescription;
    @JsonProperty("location")
    private List<LocationDTO> locationDTOList;
}
