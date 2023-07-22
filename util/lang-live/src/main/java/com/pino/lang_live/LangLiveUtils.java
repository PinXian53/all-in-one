package com.pino.lang_live;

import com.pino.exception.InternalServerErrorException;
import com.pino.ok_http.OkHttpUtils;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;

public class LangLiveUtils {

    private LangLiveUtils(){}

    private static final String LANG_LIVE_MAIN_URL = "https://www.lang.live/main/";
    private static final String LANG_LIVE_ROOM_URL = "https://www.lang.live/room/";
    private static final String LANG_LIVE_API_URL = "https://api.lang.live/langweb/v1/room/liveinfo?room_id=";

    public static boolean isLiving(String id) {
        try {
            var url = LANG_LIVE_API_URL + id;
            var response = OkHttpUtils.getRequest(url);
            return checkIsLiving(response);
        } catch (IOException e) {
            throw new InternalServerErrorException(e);
        }
    }

    public static URI getLangLiveMainUrl(String langLiveId) {
        return URI.create(LANG_LIVE_MAIN_URL + langLiveId);
    }

    public static URI getLangLiveRoomUrl(String langLiveId) {
        return URI.create(LANG_LIVE_ROOM_URL + langLiveId);
    }

    private static boolean checkIsLiving(String response) {
        return LangLiveStatus.LIVE.getValue() == new JSONObject(response)
            .getJSONObject("data")
            .getJSONObject("live_info")
            .getInt("live_status");
    }

}
