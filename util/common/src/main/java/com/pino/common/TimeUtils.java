package com.pino.common;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

public final class TimeUtils {

    public static final String TAIWAN_ZONE_OFFSET_ID = "+08:00";
    public static final ZoneOffset TAIWAN_ZONE_OFFSET = ZoneOffset.of(TAIWAN_ZONE_OFFSET_ID);

    private TimeUtils() {
    }

    public static OffsetDateTime toOffsetDateTime(Long millis) {
        return Optional.ofNullable(millis)
            .map(utcMillis -> Instant.ofEpochMilli(utcMillis).atOffset(TAIWAN_ZONE_OFFSET))
            .orElse(null);
    }

    public static String getCurrentStringDate(DateTimeFormatter dateFormatter) {
        var now = OffsetDateTime.now(TAIWAN_ZONE_OFFSET);
        return dateFormatter.format(now);
    }

    public static String getStringDate(OffsetDateTime offsetDateTime) {
        return String.format("%04d%02d%02d", offsetDateTime.getYear(),
            offsetDateTime.getMonthValue(),
            offsetDateTime.getDayOfMonth());
    }

    public static String getStringDateTime(OffsetDateTime offsetDateTime) {
        return String.format("%04d/%02d/%02d %02d:%02d", offsetDateTime.getYear(),
            offsetDateTime.getMonthValue(),
            offsetDateTime.getDayOfMonth(),
            offsetDateTime.getHour(),
            offsetDateTime.getMinute());
    }

    public static String getStringTime(OffsetDateTime offsetDateTime) {
        return String.format("%02d:%02d",
            offsetDateTime.getHour(),
            offsetDateTime.getMinute());
    }

    public static OffsetDateTime getDate(OffsetDateTime offsetDateTime) {
        return OffsetDateTime.of(offsetDateTime.getYear(), offsetDateTime.getMonthValue(),
            offsetDateTime.getDayOfMonth(), 0, 0, 0, 0, offsetDateTime.getOffset());
    }

    public static OffsetDateTime toOffsetDateTime(int year, int month, int day, ZoneOffset offset) {
        return OffsetDateTime.of(year, month, day, 0, 0, 0, 0, offset);
    }

    public static Long toUTCMilliseconds(OffsetDateTime offsetDateTime) {
        if (offsetDateTime == null) {
            return null;
        } else {
            return offsetDateTime.toInstant().atOffset(ZoneOffset.UTC).toInstant().toEpochMilli();
        }
    }

}

