package com.pino.lang_live;

import com.linecorp.bot.model.message.Message;
import com.pino.common.ThreadUtils;
import com.pino.common.TimeUtils;
import com.pino.db.dao.LangLiveStreamerDAO;
import com.pino.db.dao.SubscriptionDAO;
import com.pino.db.model.entity.LangLiveStreamerEntity;
import com.pino.enums.ServiceTypes;
import com.pino.imgur.ImgurService;
import com.pino.lang_live.model.dto.LangInfoDTO;
import com.pino.line.LineBotService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@Service
public class LangLiveService {

    private final LangLiveStreamerDAO langLiveStreamerDAO;
    private final LineBotService lineBotService;
    private final ImgurService imgurService;
    private final SubscriptionDAO subscriptionDAO;

    public void updateLiveInfo() {
        var subscribeDataDTOList = subscriptionDAO.findSubscribeData(ServiceTypes.LANG_LIVE);
        try {
            var entityList = langLiveStreamerDAO.findAll();
            for (var entity : entityList) {
                var live = LangLiveUtils.isLiving(entity.getId());
                if (Objects.equals(LangLiveStatus.UNLIVE.getValue(), entity.getStatus()) && live) {
                    // 開播
                    entity.setStatus(LangLiveStatus.LIVE.getValue());
                    entity.setLastLiveStartDateTime(OffsetDateTime.now());
                    entity.setLastLiveEndDateTime(null);
                    langLiveStreamerDAO.save(entity);
                    // 推播通知
                    var langInfoDTOList = getLiveInfo(Collections.singletonList(entity));
                    var message = LineFlexMessageSupplier.genMessage(langInfoDTOList, true);
                    subscribeDataDTOList.forEach(dto ->
                        lineBotService.pushMessage(dto.getUserId(), message)
                    );
                } else if (Objects.equals(entity.getStatus(), LangLiveStatus.LIVE.getValue()) && !live) {
                    // 關播
                    entity.setStatus(LangLiveStatus.UNLIVE.getValue());
                    entity.setLastLiveEndDateTime(OffsetDateTime.now());
                    langLiveStreamerDAO.save(entity);
                }
                ThreadUtils.sleep(26000);
            }
        } catch (Exception e) {
            e.printStackTrace();
            ThreadUtils.sleep(55000);
        }
    }

    public List<LangInfoDTO> getLiveInfo() {
        var sort = Sort.by(Sort.Direction.DESC, "status", "lastLiveStartDateTime");
        var entityList = langLiveStreamerDAO.findAll(sort);
        return getLiveInfo(entityList);
    }

    public List<LangInfoDTO> getLiveInfo(List<LangLiveStreamerEntity> entityList) {
        var langInfoDTOList = new ArrayList<LangInfoDTO>();
        for (var entity : entityList) {
            var live = entity.getStatus() == 1;
            var name = entity.getName();
            var id = entity.getId();
            var ig = entity.getIg();
            var lastLiveStartDateTime = new StringBuilder();
            String startSateTime = null;
            if (entity.getLastLiveStartDateTime() != null) {
                startSateTime = TimeUtils.getStringDateTime(entity.getLastLiveStartDateTime()
                    .toInstant().atOffset(TimeUtils.TAIWAN_ZONE_OFFSET));
            }
            String endTime = null;
            if (entity.getLastLiveEndDateTime() != null) {
                endTime = TimeUtils.getStringTime(entity.getLastLiveEndDateTime()
                    .toInstant().atOffset(TimeUtils.TAIWAN_ZONE_OFFSET));
            }
            if (startSateTime != null) {
                lastLiveStartDateTime.append(startSateTime);
            }
            if (endTime != null) {
                lastLiveStartDateTime.append("~").append(endTime);
            }
            var imageId = imgurService.getRandomAkb48TeamTpImageId(name);
            langInfoDTOList.add(
                new LangInfoDTO(name, live, entity.getId(), lastLiveStartDateTime.toString(),
                    ig, imageId, LangLiveUtils.getLangLiveMainUrl(id), LangLiveUtils.getLangLiveRoomUrl(id))
            );
        }

        return langInfoDTOList;
    }

}
