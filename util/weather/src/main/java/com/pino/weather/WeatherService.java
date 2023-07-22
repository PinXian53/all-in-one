package com.pino.weather;

import com.pino.common.EmojiUtils;
import com.pino.common.ObjectMapperUtils;
import com.pino.common.TimeUtils;
import com.pino.ok_http.OkHttpUtils;
import com.pino.weather.model.dto.WeatherResponseDTO;
import com.pino.weather.model.dto.WeatherResultDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class WeatherService {

    // 中央氣象局 - 氣象資料開放平台
    // https://opendata.cwb.gov.tw/index
    // 中央氣象局 - Open Api Swagger 文件
    // https://opendata.cwb.gov.tw/dist/opendata-swagger.html

    // 一般天氣預報-今明 36 小時天氣預報 api
    private static final String API_URL = "https://opendata.cwb.gov.tw/api/v1/rest/datastore/F-C0032-001";
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    @Value("${taiwan.cwb.authorization}")
    private String authorization;

    public String getTodayWeather() {
        return getTodayWeather(null);
    }

    public String getTodayWeather(List<String> locations) {
        try {
            var params = new HashMap<String, String>();
            params.put("Authorization", authorization);
            if (locations != null && !locations.isEmpty()) {
                params.put("locationName", String.join(",", locations));
            }

            var weatherResponseDTO = ObjectMapperUtils.readValue(
                OkHttpUtils.getRequest(API_URL, params), WeatherResponseDTO.class);
            var recordDTO = weatherResponseDTO.getRecordDTO();
            var title = recordDTO.getDatasetDescription();
            var result = transferFormat(weatherResponseDTO);

            var sb = new StringBuilder();
            sb.append("        ").append("\uD83D\uDD14 ").append(title).append(" \uD83D\uDD14").append("\n");
            for (var entry : result.entrySet()) {
                var location = entry.getKey();
                var weatherResultDTOMap = entry.getValue();
                sb.append("\n\uD83D\uDDFA ").append(location);
                for (var weatherResultDTOEntry : weatherResultDTOMap.entrySet()) {
                    var time = weatherResultDTOEntry.getKey();
                    var weatherResultDTO = weatherResultDTOEntry.getValue();
                    sb.append("\n    ").append(time).append("\n");
                    sb.append("        \uD83D\uDD39 天氣：").append(weatherResultDTO.getWeather())
                        .append(" ")
                        .append(EmojiUtils.getWeatherEmoji(weatherResultDTO.getWeather()))
                        .append("\n");
                    sb.append("        \uD83D\uDD39 體感：").append(weatherResultDTO.getPerceived())
                        .append("\n");
                    sb.append("        \uD83D\uDD39 降雨機率：")
                        .append(weatherResultDTO.getProbabilityOfPrecipitation())
                        .append("%").append("\n");
                    sb.append("        \uD83D\uDD39 氣溫：")
                        .append(weatherResultDTO.getMinTemperature())
                        .append("°")
                        .append(" ~ ").append(weatherResultDTO.getMaxTemperature()).append("°")
                        .append("\n");
                }
            }
            sb.append("\n==========================\n\n");
            sb.append("更多資訊：\n")
                .append("\uD83C\uDF10 中央氣象局：https://www.cwb.gov.tw/V8/C/W/County/County.html?CID=10004\n")
                .append("\uD83C\uDF10 天氣即時預報：https://www.facebook.com/weather.taiwan/\n");

            return sb.toString();
        } catch (Exception e) {
            log.error("getTodayWeather failed", e);
            return "發生錯誤 \uD83D\uDE2D\uD83D\uDE2D";
        }
    }

    private Map<String, Map<String, WeatherResultDTO>> transferFormat(WeatherResponseDTO weatherResponseDTO) {
        var result = new LinkedHashMap<String, Map<String, WeatherResultDTO>>();
        var recordDTO = weatherResponseDTO.getRecordDTO();
        var locationDTOList = recordDTO.getLocationDTOList();
        for (var locationDTO : locationDTOList) {
            var locationName = locationDTO.getLocationName();
            result.putIfAbsent(locationName, new LinkedHashMap<>());
            var weatherElementDTOList = locationDTO.getWeatherElementDTOList();
            for (var weatherElementDTO : weatherElementDTOList) {
                var elementName = weatherElementDTO.getElementName();
                var timeDTOList = weatherElementDTO.getTimeDTOList();
                for (var timeDTO : timeDTOList) {
                    var time = getTimeRange(timeDTO.getStartTime());
                    var value = timeDTO.getParameterDTO().getParameterName();
                    result.get(locationName).putIfAbsent(time, new WeatherResultDTO());
                    var weatherResultDTO = result.get(locationName).get(time);
                    switch (elementName) {
                        case "Wx" -> weatherResultDTO.setWeather(value);
                        case "PoP" -> weatherResultDTO.setProbabilityOfPrecipitation(value);
                        case "MinT" -> weatherResultDTO.setMinTemperature(value);
                        case "MaxT" -> weatherResultDTO.setMaxTemperature(value);
                        case "CI" -> weatherResultDTO.setPerceived(value);
                        default -> {
                        }
                    }
                }
            }
        }
        return result;
    }

    // 支援格式：2022-04-10 18:00:00
    private String getTimeRange(String time) {
        var now = OffsetDateTime.now(TimeUtils.TAIWAN_ZONE_OFFSET);
        var date = LocalDateTime.parse(time, DATE_FORMAT);
        var resourceTime = OffsetDateTime.of(date.getYear(), date.getMonthValue(),
            date.getDayOfMonth(), date.getHour(), date.getMinute(), date.getSecond(), 0,
            TimeUtils.TAIWAN_ZONE_OFFSET);

        String prefix;

        if (TimeUtils.getDate(now).isEqual(TimeUtils.getDate(resourceTime))) {
            prefix = "今天";
        } else if (TimeUtils.getDate(now.plusDays(1)).isEqual(TimeUtils.getDate(resourceTime))) {
            prefix = "明天";
        } else if (TimeUtils.getDate(now.plusDays(2)).isEqual(TimeUtils.getDate(resourceTime))) {
            prefix = "後天";
        } else {
            prefix = TimeUtils.getStringDate(resourceTime);
        }

        var hour = resourceTime.getHour();
        var timeRange = switch (hour) {
            case 6 -> "白天";
            case 18 -> "晚上";
            default -> hour + " 點";
        };
        return prefix + " " + timeRange;
    }
}

