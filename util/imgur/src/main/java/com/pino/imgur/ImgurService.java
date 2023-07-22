package com.pino.imgur;

import com.pino.db.dao.ImgurDAO;
import com.pino.db.model.entity.ImgurEntity;
import com.pino.enums.ImageTypeTags;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@RequiredArgsConstructor
@Service
public class ImgurService {

    private final ImgurDAO imgurDAO;

    public String getRandomAkb48TeamTpImageId() {
        var entityList = imgurDAO.findByTypeTag(ImageTypeTags.AKB48_TEAM_TP);
        return getRandomId(entityList);
    }

    public String getRandomAkb48TeamTpImageId(String personTag) {
        var entityList = imgurDAO.findByTypeTagAndPersonTag(ImageTypeTags.AKB48_TEAM_TP, personTag);
        return getRandomId(entityList);
    }

    private String getRandomId(List<ImgurEntity> entityList) {
        if (entityList.isEmpty()) {
            return null;
        }
        var randomNum = getRandomIndex(entityList.size());
        return entityList.get(randomNum).getImageId();
    }

    private int getRandomIndex(long count) {
        var min = 0;
        var max = (int) count - 1;
        return ThreadLocalRandom.current().nextInt(min, max + 1);
    }

}
