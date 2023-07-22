package com.pino.google_trend.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ArticleDTO {
    @JsonProperty("title")
    private String title;
    @JsonProperty("timeAgo")
    private String timeAgo;
    @JsonProperty("source")
    private String source;
    @JsonProperty("url")
    private String url;
    @JsonProperty("snippet")
    private String snippet;
}
