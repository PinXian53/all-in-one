package com.pino.lang_live;

import java.util.Objects;

public enum LangLiveStatus {

    UNLIVE(0),
    LIVE(1);

    private final Integer value;

    LangLiveStatus(Integer value) {
        this.value = value;
    }

    public static LangLiveStatus fromValue(Integer value) {
        LangLiveStatus result = null;
        if (Objects.nonNull(value)) {
            for (LangLiveStatus langLiveStatus : LangLiveStatus.values()) {
                if (Objects.equals(langLiveStatus.getValue(), value)) {
                    result = langLiveStatus;
                    break;
                }
            }
        }
        return result;
    }

    public Integer getValue() {
        return value;
    }
}
