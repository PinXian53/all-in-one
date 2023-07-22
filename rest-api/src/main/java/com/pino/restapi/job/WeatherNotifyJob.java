package com.pino.restapi.job;

import com.pino.restapi.service.LineBotPushMessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@EnableScheduling
@Component
public class WeatherNotifyJob {

    private final LineBotPushMessageService lineBotPushMessageService;

    // 每天早上 7 點，推播當日天氣
    @Scheduled(cron = "0 0 7 * * ?", zone = "Asia/Taipei")
    public void execute() {
        log.info("WeatherNotifyJob start");
        lineBotPushMessageService.pushWeatherMessage();
        log.info("WeatherNotifyJob end");
    }
}
