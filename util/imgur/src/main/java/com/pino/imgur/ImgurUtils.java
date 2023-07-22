package com.pino.imgur;

import com.pino.enums.ImgurSize;
import com.pino.exception.InternalServerErrorException;

import java.net.URI;

public class ImgurUtils {

    private ImgurUtils(){}

    private static final String IMGUR_IMAGE_URL_PREFIX = "https://i.imgur.com/";

    public static URI getImgurUri(String imgurId) {
        return URI.create(IMGUR_IMAGE_URL_PREFIX + imgurId);
    }

    public static URI getImgurUri(String imgurId, ImgurSize imgurSize) {
        return URI.create(IMGUR_IMAGE_URL_PREFIX + convertSize(imgurId, imgurSize));
    }

    public static String convertSize(String imgurId, ImgurSize imgurSize) {
        var contentArray = imgurId.split("\\.");
        if (contentArray.length != 2) {
            throw new InternalServerErrorException("Imgur convert size error");
        }
        return contentArray[0] + imgurSize.getValue() + "." + contentArray[1];
    }

}