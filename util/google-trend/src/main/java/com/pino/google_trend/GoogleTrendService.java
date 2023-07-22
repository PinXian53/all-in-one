package com.pino.google_trend;

import com.pino.common.EmojiUtils;
import com.pino.common.ObjectMapperUtils;
import com.pino.common.TimeUtils;
import com.pino.exception.InternalServerErrorException;
import com.pino.google_trend.model.GoogleTrendResponseDTO;
import com.pino.google_trend.model.TrendingSearchDTO;
import com.pino.google_trend.model.TrendingSearchesDayDTO;
import com.pino.ok_http.OkHttpUtils;
import com.pino.short_url.ShortUrlService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class GoogleTrendService {

    private static final String GOOGLE_TREND_URL = "https://trends.google.com.tw/trends/api/dailytrends";
    private static final String RESPONSE_PREFIX = ")]}',\n";
    private static final int FIRST_ELEMENT_INDEX = 0;
    private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyMMdd");

    private final ShortUrlService shortUrlService;

    public String getTodayGoogleTrendContent(Integer limit, boolean useShortUrl) {

        var googleTrendResponseDTO = getTodayGoogleTrend();
        var searchesDayDTO = googleTrendResponseDTO
            .getDefaultDTO()
            .getTrendingSearchesDayDTOList()
            .get(FIRST_ELEMENT_INDEX);
        return renderGoogleTrendContent(searchesDayDTO, limit, useShortUrl);
    }

    private GoogleTrendResponseDTO getTodayGoogleTrend() {
        try {
            var requestParams = Map.of(
                "hl", "zh-TW",
                "tz", "-480",
                "ed", TimeUtils.getCurrentStringDate(dateFormatter),
                "geo", "TW",
                "ns", "15"
            );

            var response = OkHttpUtils.getRequest(GOOGLE_TREND_URL, requestParams);
            var jsonString = response.substring(RESPONSE_PREFIX.length());

            return ObjectMapperUtils.readValue(jsonString, GoogleTrendResponseDTO.class);
        } catch (Exception e) {
            throw new InternalServerErrorException(e);
        }
    }

    private String renderGoogleTrendContent(TrendingSearchesDayDTO searchesDayDTO, Integer limit, boolean useShortUrl) {
        var trendingSearchDTOList = searchesDayDTO.getTrendingSearchDTOList();

        var sb = new StringBuilder();
        sb.append("Google Trend 台灣\n");
        sb.append(searchesDayDTO.getFormattedDate()).append("\n\n");
        int index = 1;
        for (TrendingSearchDTO dto : trendingSearchDTOList) {
            sb.append(EmojiUtils.getNumberEmoji(index))
                .append(" ")
                .append(dto.getTitleDTO().getQuery())
                .append(" - ")
                .append(dto.getFormattedTraffic()).append("\n");
            var firstArticle = dto.getArticleDTOList().get(FIRST_ELEMENT_INDEX);
            String url = useShortUrl
                ? shortUrlService.registerShortUrl(firstArticle.getUrl())
                : firstArticle.getUrl();
            sb.append("    ⊜ ").append(firstArticle.getTitle()).append("\n");
            sb.append("    ⊜ ").append(firstArticle.getSource()).append("：").append(url)
                .append("\n\n");

            if (limit != null && index >= limit) {
                break;
            }
            index++;
        }
        return sb.toString();
    }
}
