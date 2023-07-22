package com.pino.ig;

import java.net.URI;

public class IgUtils {

    private IgUtils(){}

    private static final String IG_URL_PREFIX = "https://instagram.com/";

    public static URI getIgUri(String igId) {
        return URI.create(IG_URL_PREFIX + igId);
    }

}
