package com.pino.db.dao.jpa;

import com.pino.db.model.entity.ShortUrlEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.OffsetDateTime;
import java.util.List;

public interface ShortUrlRepository extends JpaRepository<ShortUrlEntity, Integer> {

    ShortUrlEntity findByKey(String key);

    boolean existsByKey(String key);

    List<ShortUrlEntity> findByExpiredDateTimeBefore(OffsetDateTime dateTime);
}
