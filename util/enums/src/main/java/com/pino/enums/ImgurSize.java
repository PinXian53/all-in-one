package com.pino.enums;

import java.util.Objects;

public enum ImgurSize {

    ORIGINAL(""),
    HUGE("h"),
    LARGE("l"),
    MEDIUM("m"),
    SMALL("t");

    private final String value;

    ImgurSize(String value) {
        this.value = value;
    }

    public static ImgurSize fromValue(String value) {
        ImgurSize result = null;
        if (Objects.nonNull(value)) {
            for (ImgurSize imgurSize : ImgurSize.values()) {
                if (imgurSize.getValue().equals(value)) {
                    result = imgurSize;
                    break;
                }
            }
        }
        return result;
    }

    public String getValue() {
        return value;
    }
}
