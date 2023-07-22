package com.pino.enums;

import java.util.Objects;

public enum ImageTypeTags {

    IG("IG"),
    AKB48_TEAM_TP("AKB48_TEAM_TP");

    private final String value;

    ImageTypeTags(String value) {
        this.value = value;
    }

    public static ImageTypeTags fromValue(String value) {
        ImageTypeTags result = null;
        if (Objects.nonNull(value)) {
            for (ImageTypeTags imageTypeTags : ImageTypeTags.values()) {
                if (imageTypeTags.getValue().equals(value)) {
                    result = imageTypeTags;
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
