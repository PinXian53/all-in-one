package com.pino.google_trend.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class TrendingSearchDTO {
    @JsonProperty("title")
    private TitleDTO titleDTO;
    @JsonProperty("formattedTraffic")
    private String formattedTraffic;
    @JsonProperty("articles")
    private List<ArticleDTO> articleDTOList;
}
