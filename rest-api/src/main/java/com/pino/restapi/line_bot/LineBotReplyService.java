package com.pino.restapi.line_bot;

import com.linecorp.bot.model.message.ImageMessage;
import com.linecorp.bot.model.message.Message;
import com.linecorp.bot.model.message.TextMessage;
import com.pino.common.UrlUtils;
import com.pino.db.dao.LineBotLogDAO;
import com.pino.db.model.entity.LineBotLogEntity;
import com.pino.google_trend.GoogleTrendService;
import com.pino.imgur.ImgurService;
import com.pino.imgur.ImgurUtils;
import com.pino.lang_live.LangLiveService;
import com.pino.lang_live.LineFlexMessageSupplier;
import com.pino.restapi.service.ImageService;
import com.pino.restapi.service.UserService;
import com.pino.short_url.ShortUrlService;
import com.pino.weather.WeatherService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.time.OffsetDateTime;
import java.util.Arrays;

@Slf4j
@RequiredArgsConstructor
@Service
public class LineBotReplyService {

    private final WeatherService weatherService;
    private final GoogleTrendService googleTrendService;
    private final UserService userService;
    private final ShortUrlService shortUrlService;
    private final ImageService imageService;
    private final ImgurService imgurService;
    private final LangLiveService langLiveStreamerService;

    private final LineBotLogDAO lineBotLogDAO;

    public Message replyTextMessageEvent(String userId, String messageText) {
        userService.ifNotExistAddUser(userId);
        saveLog("TextMessageEvent", userId, messageText);

        if (messageText.startsWith("/")) {
            var command = messageText.split(" ");
            switch (command[0]) {
                case "/weather":
                    var parameters = new String[command.length - 1];
                    System.arraycopy(command, 1, parameters, 0, command.length - 1);
                    return new TextMessage(weatherService.getTodayWeather(Arrays.asList(parameters)));
                case "/trend":
                    var useShortUrl = userService.isAdmin(userId);
                    return new TextMessage(googleTrendService.getTodayGoogleTrendContent(10, useShortUrl));
                case "/ig-image":
                    return imageService.getRandomIgImage();
                case "/akb-image":
                    String imgurId = null;
                    if (command.length > 1) {
                        imgurId = imgurService.getRandomAkb48TeamTpImageId(command[1]);
                    }
                    if (imgurId == null) {
                        imgurId = imgurService.getRandomAkb48TeamTpImageId();
                    }
                    var akbImageUri = ImgurUtils.getImgurUri(imgurId);
                    return new ImageMessage(akbImageUri, akbImageUri);
                case "/about":
                    return new TextMessage(getHelpMessage(userService.isAdmin(userId)));
                case "/akb48-tp":
                    return LineFlexMessageSupplier.genMessage(
                        langLiveStreamerService.getLiveInfo(), false);
                case "/short-url":
                    if (command.length >= 2 && userService.isAdmin(userId)) {
                        return new TextMessage(shortUrlService.registerShortUrl(command[1].trim()));
                    }
                    break;
                default:
            }
        }
        return new TextMessage(messageText);
    }

    public Message replyPostbackEvent(String userId, String data) {
        saveLog("PostbackEvent", userId, data);

        var urlObject = UrlUtils.parseUrl(data);
        var url = urlObject.getUrl();
        var params = urlObject.getParams();
        switch (url) {
            case "image":
                if (params.containsKey("id")) {
                    var imageUri = URI.create(imageService.getImageUrl(params.get("id")));
                    return new ImageMessage(imageUri, imageUri);
                }
            case "imgur":
                if (params.containsKey("id")) {
                    var imageUri = ImgurUtils.getImgurUri(params.get("id"));
                    return new ImageMessage(imageUri, imageUri);
                }
            default:
        }
        throw new IllegalArgumentException("Unknown url");
    }

    private void saveLog(String event, String userId, String messageText) {
        var log = new LineBotLogEntity();
        log.setEvent(event);
        log.setUserId(userId);
        log.setMessageText(messageText);
        log.setCreateDateTime(OffsetDateTime.now());
        lineBotLogDAO.save(log);
    }

    private String getHelpMessage(boolean isAdmin) {
        var sb = new StringBuilder();
        sb.append("\uD83D\uDCAC支援指令清單：\n");
        sb.append("/weather      : 天氣資訊\n");
        sb.append("/trend           : Google 搜尋趨勢\n");
        sb.append("/ig-image     : 隨機 IG 照片\n");
        sb.append("/akb-image  : 隨機 AKB 照片\n");
        sb.append("/akb48-tp     : AKB48 Team TP 資訊\n");
        sb.append("/about          : 關於我");
        if (isAdmin) {
            sb.append("\n\n\uD83D\uDC68\uD83C\uDFFB\u200D\uD83D\uDCBB管理員專用指令：\n");
            sb.append("/short-url   : 短網址轉換\n");
            sb.append("範例：/short-url https://www.google.com");
        }
        return sb.toString();
    }
}