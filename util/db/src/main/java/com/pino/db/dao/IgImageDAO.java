package com.pino.db.dao;

import com.pino.db.dao.jpa.IgImageRepository;
import com.pino.db.model.entity.IgImageEntity;
import org.springframework.stereotype.Repository;

@Repository
public class IgImageDAO extends BaseDAO<IgImageEntity, Integer> {

    private final IgImageRepository igImageRepository;

    public IgImageDAO(IgImageRepository igImageRepository) {
        super(igImageRepository);
        this.igImageRepository = igImageRepository;
    }

    private IgImageEntity findByFileId(String fileId) {
        return igImageRepository.findByFileId(fileId);
    }
}
