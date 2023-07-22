package com.pino.restapi.controller;

import com.pino.google_trend.GoogleTrendService;
import com.pino.restapi.model.ContentDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("googleTrend")
public class GoogleTrendController {

    private final GoogleTrendService googleTrendService;

    @GetMapping
    public ContentDTO getTodayGoogleTrend() {
        return ContentDTO.of(googleTrendService.getTodayGoogleTrendContent(null, false));
    }

}

