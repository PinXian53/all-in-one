package com.pino.db.dao.jpa;

import com.pino.db.model.entity.ImgurEntity;
import com.pino.enums.ImageTypeTags;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImgurRepository extends JpaRepository<ImgurEntity, Integer> {

    List<ImgurEntity> findByTypeTag(ImageTypeTags typeTag);

    List<ImgurEntity> findByTypeTagAndPersonTag(ImageTypeTags typeTag, String personTag);

}
