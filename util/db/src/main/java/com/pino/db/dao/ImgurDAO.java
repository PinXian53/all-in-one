package com.pino.db.dao;

import com.pino.db.dao.jpa.ImgurRepository;
import com.pino.db.model.entity.ImgurEntity;
import com.pino.enums.ImageTypeTags;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ImgurDAO extends BaseDAO<ImgurEntity, Integer> {
    private final ImgurRepository imgurRepository;

    public ImgurDAO(ImgurRepository imgurRepository) {
        super(imgurRepository);
        this.imgurRepository = imgurRepository;
    }

    public List<ImgurEntity> findByTypeTag(ImageTypeTags typeTag) {
        return imgurRepository.findByTypeTag(typeTag);
    }

    public List<ImgurEntity> findByTypeTagAndPersonTag(ImageTypeTags typeTag, String personTag) {
        return imgurRepository.findByTypeTagAndPersonTag(typeTag, personTag);
    }
}
