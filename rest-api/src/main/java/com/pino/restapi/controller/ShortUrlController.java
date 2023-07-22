package com.pino.restapi.controller;

import com.pino.short_url.ShortUrlService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("s")
public class ShortUrlController {

    private static final String LOCATION = "Location";

    private final ShortUrlService shortUrlService;

    @GetMapping("{key}")
    public void redirect(HttpServletResponse response, @PathVariable String key) {
        response.setHeader(LOCATION, shortUrlService.getUrl(key)); // 重新導向的 url
        response.setStatus(HttpServletResponse.SC_FOUND);
    }
}
