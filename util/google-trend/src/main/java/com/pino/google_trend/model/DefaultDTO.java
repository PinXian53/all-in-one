package com.pino.google_trend.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class DefaultDTO {
    @JsonProperty("trendingSearchesDays")
    private List<TrendingSearchesDayDTO> trendingSearchesDayDTOList;
    @JsonProperty("endDateForNextRequest")
    private String endDateForNextRequest;
}
