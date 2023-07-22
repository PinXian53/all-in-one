package com.pino.db.dao;

import com.pino.db.dao.jpa.TelegramUserRepository;
import com.pino.db.model.entity.TelegramUserEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TelegramUserDAO extends BaseDAO<TelegramUserEntity, Integer> {
    private final TelegramUserRepository telegramUserRepository;

    public TelegramUserDAO(TelegramUserRepository telegramUserRepository) {
        super(telegramUserRepository);
        this.telegramUserRepository = telegramUserRepository;
    }

    public TelegramUserEntity findByChatId(String chatId) {
        return telegramUserRepository.findByChatId(chatId);
    }

    public List<TelegramUserEntity> findBySub(Boolean sub) {
        return telegramUserRepository.findBySub(sub);
    }
}
