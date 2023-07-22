package com.pino.common;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ThreadUtils {

    private ThreadUtils() {}

    public static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            log.error("sleep failed：{}", e.getMessage());
            Thread.currentThread().interrupt();
        }
    }
}
