package com.pino.restapi.controller;

import com.pino.restapi.model.ContentDTO;
import com.pino.restapi.service.LineBotPushMessageService;
import com.pino.short_url.ShortUrlService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.web.bind.annotation.*;

@ConditionalOnProperty(prefix = "api", name = "test-mode", havingValue = "true")
@RestController
@RequiredArgsConstructor
@RequestMapping("test")
public class TestController {

    private final ShortUrlService shortUrlService;
    private final LineBotPushMessageService lineBotPushMessageService;

    @GetMapping("s/register")
    public ContentDTO register(@RequestParam String url) {
        return ContentDTO.of(shortUrlService.registerShortUrl(url));
    }

    @PostMapping("weatherNotify")
    public ContentDTO executeWeatherNotifyJob() {
        lineBotPushMessageService.pushWeatherMessage();
        return ContentDTO.of("success");
    }
}
