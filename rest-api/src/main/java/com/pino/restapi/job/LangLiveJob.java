package com.pino.restapi.job;

import com.pino.common.TimeUtils;
import com.pino.lang_live.LangLiveService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;

@Slf4j
@RequiredArgsConstructor
@Data
@EnableScheduling
@Component
public class LangLiveJob {

    private final LangLiveService langLiveService;

    // 每 10 分鐘，爬一次 Lang Live 開播狀態
    @Scheduled(fixedDelay = 600000)
    public void updateLiveInfo() {
        if (inSleepTime()) {
            log.info("updateLiveInfo sleeping (0-8H)");
            return;
        }

        log.info("updateLiveInfo start");
        langLiveService.updateLiveInfo();
        log.info("updateLiveInfo end");
    }

    private boolean inSleepTime() {
        var hour = OffsetDateTime.now().toInstant()
            .atOffset(TimeUtils.TAIWAN_ZONE_OFFSET)
            .getHour();
        return hour >= 0 && hour <= 8;
    }
}
