package com.pino.db.dao;

import com.pino.db.dao.jpa.LineBotLogRepository;
import com.pino.db.model.entity.LineBotLogEntity;
import org.springframework.stereotype.Repository;

@Repository
public class LineBotLogDAO extends BaseDAO<LineBotLogEntity, Integer> {

    private final LineBotLogRepository lineBotLogRepository;

    public LineBotLogDAO(LineBotLogRepository lineBotLogRepository) {
        super(lineBotLogRepository);
        this.lineBotLogRepository = lineBotLogRepository;
    }
}
