package com.pino.restapi.service;

import com.linecorp.bot.model.action.PostbackAction;
import com.linecorp.bot.model.action.URIAction;
import com.linecorp.bot.model.message.TemplateMessage;
import com.linecorp.bot.model.message.template.ButtonsTemplate;
import com.pino.db.dao.IgImageDAO;
import com.pino.ig.IgUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

@RequiredArgsConstructor
@Service
public class ImageService {

    private static final String GOOGLE_DRIVE_IMAGE_URL_PREFIX = "https://drive.google.com/uc?id=";

    private final IgImageDAO igImageDAO;

    public TemplateMessage getRandomIgImage() {
        var randomNum = getRandomIndex(igImageDAO.count());
        var igImageEntity = igImageDAO.findAll(PageRequest.of(randomNum, 1)).getContent().get(0);
        return genIgMessage(igImageEntity.getFileName().split("-")[0], igImageEntity.getFileId());
    }

    public String getImageUrl(String id) {
        return GOOGLE_DRIVE_IMAGE_URL_PREFIX + id;
    }

    private int getRandomIndex(long count) {
        var min = 0;
        var max = (int) count - 1;
        return ThreadLocalRandom.current().nextInt(min, max + 1);
    }

    public TemplateMessage genIgMessage(String igId, String googleFileId) {
        var uriAction = new URIAction("IG", IgUtils.getIgUri(igId), null);
        var postbackAction = new PostbackAction("Original Image", "image?id=" + googleFileId, null);
        var buttonsTemplate = ButtonsTemplate.builder()
            .thumbnailImageUrl(URI.create(getImageUrl(googleFileId)))
            .title(igId)
            .text(" ")
            .imageAspectRatio("square")
            .defaultAction(uriAction)
            .actions(Arrays.asList(uriAction, postbackAction))
            .build();
        return new TemplateMessage("IG Photo", buttonsTemplate);
    }

}
