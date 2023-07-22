package com.pino.enums;

import java.util.Objects;

public enum ServiceTypes {

    WEATHER("Weather"),
    GOOGLE_TREND("GoogleTrend"),
    LANG_LIVE("LangLive");

    private final String value;

    ServiceTypes(String value) {
        this.value = value;
    }

    public static ServiceTypes fromValue(String value) {
        ServiceTypes result = null;
        if (Objects.nonNull(value)) {
            for (ServiceTypes serviceTypes : ServiceTypes.values()) {
                if (serviceTypes.getValue().equals(value)) {
                    result = serviceTypes;
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
