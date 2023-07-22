package com.pino.google_trend.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class TitleDTO {
    @JsonProperty("query")
    private String query;
    @JsonProperty("exploreLink")
    private String exploreLink;
}
