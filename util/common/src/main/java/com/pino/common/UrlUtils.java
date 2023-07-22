package com.pino.common;

import com.pino.common.model.UrlObject;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public final class UrlUtils {

    private UrlUtils() {
    }

    public static String getUrl(String url, Map<String, String> params) {
        StringBuilder sb = new StringBuilder();
        sb.append(url);
        if (params != null && !params.isEmpty()) {
            sb.append("?");
            for (Map.Entry<String, String> entry : params.entrySet()) {
                sb.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
            }
            sb.deleteCharAt(sb.length() - 1);
        }
        return sb.toString();
    }

    public static UrlObject parseUrl(String url) {
        UrlObject urlObject = new UrlObject();
        Map<String, String> params = new HashMap<>();
        if (url.contains("?")) {
            int i = url.indexOf("?");
            urlObject.setUrl(url.substring(0, i));
            String paramString = url.substring(i + 1);
            Arrays.stream(paramString.split("&")).forEach(s -> {
                if (s.contains("=")) {
                    String[] param = s.split("=");
                    if (param.length == 2) {
                        params.put(param[0], param[1]);
                    }
                }
            });
        } else {
            urlObject.setUrl(url);
        }
        urlObject.setParams(params);
        return urlObject;
    }

}