package com.pino.common.model;

import lombok.Data;

import java.util.Map;

@Data
public class UrlObject {

    private String url;
    private Map<String, String> params;
}