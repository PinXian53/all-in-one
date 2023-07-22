package com.pino.google_trend.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class TrendingSearchesDayDTO {
    @JsonProperty("date")
    private String date;
    @JsonProperty("formattedDate")
    private String formattedDate;
    @JsonProperty("trendingSearches")
    private List<TrendingSearchDTO> trendingSearchDTOList;
}
