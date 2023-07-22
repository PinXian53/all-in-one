package com.pino.db.dao.jpa;

import com.pino.db.model.entity.TelegramUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TelegramUserRepository extends JpaRepository<TelegramUserEntity, Integer> {

    TelegramUserEntity findByChatId(String chatId);

    List<TelegramUserEntity> findBySub(Boolean sub);

}
