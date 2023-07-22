package com.pino.short_url;

import com.pino.db.dao.ShortUrlDAO;
import com.pino.db.model.entity.ShortUrlEntity;
import com.pino.exception.InternalServerErrorException;
import com.pino.exception.ShortUrlNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class ShortUrlService {

    public static final int DEFAULT_CLICK_COUNT = 0;
    public static final int EXPIRED_DAY = 14;
    public static final int KEY_LENGTH = 6;
    public static final int MAX_RETRY_COUNT = 5;

    /**
     * template: {web-url}/s/{key} <br/>
     * example: http://pino.xxx.com/s/41fdf0
     */
    public static final String SHORT_URL_TEMPLATE = "%s/s/%s";

    private final ShortUrlDAO shortUrlDAO;

    @Value("${web.url}")
    private String webUrl;

    public String getUrl(String key) {
        var shortUrlEntity = shortUrlDAO.findByKey(key);
        if (shortUrlEntity == null) {
            throw new ShortUrlNotFoundException();
        }
        shortUrlEntity.setClickCount(shortUrlEntity.getClickCount() + 1);
        shortUrlDAO.save(shortUrlEntity);
        return shortUrlEntity.getUrl();
    }

    public String registerShortUrl(String url) {
        var key = generateKey();
        var shortUrlEntity = ShortUrlEntity.builder()
            .key(key)
            .url(url)
            .clickCount(DEFAULT_CLICK_COUNT)
            .expiredDateTime(OffsetDateTime.now().plusDays(EXPIRED_DAY))
            .build();
        shortUrlDAO.save(shortUrlEntity);
        return SHORT_URL_TEMPLATE.formatted(webUrl, key);
    }

    public void deleteExpiredUrl() {
        var expiredUrlList = shortUrlDAO.findByExpiredDateTimeBefore(OffsetDateTime.now());
        shortUrlDAO.deleteAll(expiredUrlList);
    }

    private String generateKey() {
        int retryCount = 0;
        String key;
        boolean keyDuplicate;

        do {
            key = UUID.randomUUID().toString().substring(0, KEY_LENGTH);
            keyDuplicate = shortUrlDAO.existsByKey(key);
            retryCount++;
        }

        while (keyDuplicate && retryCount < MAX_RETRY_COUNT);

        if (keyDuplicate) {
            throw new InternalServerErrorException("key always duplicate");
        }
        return key;
    }
}
