package com.pino.db.dao.jpa;

import com.pino.db.model.entity.IgImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IgImageRepository extends JpaRepository<IgImageEntity, Integer> {

    IgImageEntity findByFileId(String fileId);

}
