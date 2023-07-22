package com.pino.lang_live.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.net.URI;

@AllArgsConstructor
@Data
public class LangInfoDTO {
    String name;
    boolean live;
    String langLiveId;
    String lastLiveDateTime;
    String ig;
    String randomImgurId;
    URI liveMainUrl;
    URI liveRoomUrl;
}
