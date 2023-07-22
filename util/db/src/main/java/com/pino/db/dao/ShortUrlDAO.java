package com.pino.db.dao;

import com.pino.db.dao.jpa.ShortUrlRepository;
import com.pino.db.model.entity.ShortUrlEntity;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;
import java.util.List;

@Repository
public class ShortUrlDAO extends BaseDAO<ShortUrlEntity, Integer> {

    private final ShortUrlRepository shortUrlRepository;

    public ShortUrlDAO(ShortUrlRepository shortUrlRepository) {
        super(shortUrlRepository);
        this.shortUrlRepository = shortUrlRepository;
    }

    public boolean existsByKey(String key) {
        return shortUrlRepository.existsByKey(key);
    }

    public ShortUrlEntity findByKey(String key) {
        return shortUrlRepository.findByKey(key);
    }

    public List<ShortUrlEntity> findByExpiredDateTimeBefore(OffsetDateTime offsetDateTime) {
        return shortUrlRepository.findByExpiredDateTimeBefore(offsetDateTime);
    }

}
