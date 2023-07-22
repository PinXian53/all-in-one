package com.pino.google_trend.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class GoogleTrendResponseDTO {
    @JsonProperty("default")
    private DefaultDTO defaultDTO;
}
