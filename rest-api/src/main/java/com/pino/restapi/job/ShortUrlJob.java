package com.pino.restapi.job;

import com.pino.short_url.ShortUrlService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Data
@EnableScheduling
@Component
public class ShortUrlJob {

    private final ShortUrlService shortUrlService;

    // 每天早上 1 點，刪除過期之短網址資料
    @Scheduled(cron = "0 0 1 * * ?", zone = "Asia/Taipei")
    public void deleteExpiredUrl() {
        log.info("deleteExpiredUrl start");
        shortUrlService.deleteExpiredUrl();
        log.info("deleteExpiredUrl end");
    }
}
