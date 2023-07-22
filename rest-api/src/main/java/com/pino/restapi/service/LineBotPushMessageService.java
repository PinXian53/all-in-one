package com.pino.restapi.service;

import com.pino.db.dao.SubscriptionDAO;
import com.pino.enums.ServiceTypes;
import com.pino.google_trend.GoogleTrendService;
import com.pino.line.LineBotService;
import com.pino.weather.WeatherService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@RequiredArgsConstructor
@Service
public class LineBotPushMessageService {

    private final SubscriptionDAO subscriptionRepository;
    private final LineBotService lineBotService;
    private final WeatherService weatherService;
    private final GoogleTrendService googleTrendService;

    public void pushWeatherMessage() {
        var subscribeDataDTOList = subscriptionRepository.findSubscribeData(ServiceTypes.WEATHER);
        subscribeDataDTOList.forEach(dto -> {
            if (dto.getParameter() != null) {
                var parameterList = Arrays.asList(dto.getParameter().split(" "));
                lineBotService.pushMessage(
                    dto.getUserId(), weatherService.getTodayWeather(parameterList)
                );
            }
        });
    }

    public void pushGoogleTrendMessage() {
        var subscribeDataDTOList = subscriptionRepository.findSubscribeData(ServiceTypes.GOOGLE_TREND);
        var todayGoogleTrend = googleTrendService.getTodayGoogleTrendContent(10, true);
        subscribeDataDTOList.forEach(dto -> lineBotService.pushMessage(
            dto.getUserId(), todayGoogleTrend
        ));
    }
}