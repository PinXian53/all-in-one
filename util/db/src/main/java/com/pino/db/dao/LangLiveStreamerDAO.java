package com.pino.db.dao;

import com.pino.db.dao.jpa.LangLiveStreamerRepository;
import com.pino.db.model.entity.LangLiveStreamerEntity;
import org.springframework.stereotype.Repository;

@Repository
public class LangLiveStreamerDAO extends BaseDAO<LangLiveStreamerEntity, Integer> {

    private final LangLiveStreamerRepository langLiveStreamerRepository;

    public LangLiveStreamerDAO(LangLiveStreamerRepository langLiveStreamerRepository) {
        super(langLiveStreamerRepository);
        this.langLiveStreamerRepository = langLiveStreamerRepository;
    }
}
