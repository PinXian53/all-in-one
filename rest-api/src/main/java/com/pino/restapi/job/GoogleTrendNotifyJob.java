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
public class GoogleTrendNotifyJob {

    private final LineBotPushMessageService lineBotPushMessageService;

    // 每天晚上 7 點，推播當日 Google 趨勢
    @Scheduled(cron = "0 0 19 * * ?", zone = "Asia/Taipei")
    public void execute() {
        log.info("GoogleTrendNotifyJob start");
        lineBotPushMessageService.pushGoogleTrendMessage();
        log.info("GoogleTrendNotifyJob end");
    }
}
