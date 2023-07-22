package com.pino.lang_live;

import com.linecorp.bot.model.action.PostbackAction;
import com.linecorp.bot.model.action.URIAction;
import com.linecorp.bot.model.message.FlexMessage;
import com.linecorp.bot.model.message.flex.component.*;
import com.linecorp.bot.model.message.flex.container.Bubble;
import com.linecorp.bot.model.message.flex.container.Carousel;
import com.linecorp.bot.model.message.flex.unit.FlexFontSize;
import com.linecorp.bot.model.message.flex.unit.FlexLayout;
import com.linecorp.bot.model.message.flex.unit.FlexMarginSize;
import com.pino.enums.ImgurSize;
import com.pino.ig.IgUtils;
import com.pino.imgur.ImgurUtils;
import com.pino.lang_live.model.dto.LangInfoDTO;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class LineFlexMessageSupplier {

    private LineFlexMessageSupplier() {}

    public static FlexMessage genMessage(List<LangInfoDTO> langInfoDTOList, boolean startLiveNotify) {
        var bubbleList = langInfoDTOList.stream()
            .map(dto -> genBubble(dto, startLiveNotify))
            .collect(Collectors.toList());

        var carousel = Carousel.builder()
            .contents(bubbleList)
            .build();

        var altText = "Lang Info";

        if (startLiveNotify) {
            var startLiveNames = langInfoDTOList.stream()
                .map(LangInfoDTO::getName)
                .collect(Collectors.joining(", "));
            altText = startLiveNames + "開播通知！";
        }

        return new FlexMessage(altText, carousel);
    }

    private static Bubble genBubble(LangInfoDTO langInfoDTO, boolean startLiveNotify) {
        var imgurId = langInfoDTO.getRandomImgurId();
        var title = langInfoDTO.getName() + (startLiveNotify ? "開播啦！" : "");
        var live = langInfoDTO.isLive();
        var lastLiveDateTime = langInfoDTO.getLastLiveDateTime();
        var ig = langInfoDTO.getIg();
        var liveMainUrl = langInfoDTO.getLiveMainUrl();
        var liveRoomUrl = langInfoDTO.getLiveRoomUrl();

        final Image heroBlock = createHeroBlock(imgurId);
        final Box bodyBlock = createBodyBlock(title, live, lastLiveDateTime);
        final Box footerBlock = createFooterBlock(liveMainUrl, liveRoomUrl, live, ig);

        return Bubble.builder()
            .hero(heroBlock)
            .body(bodyBlock)
            .footer(footerBlock)
            .size(Bubble.BubbleSize.KILO)
            .build();
    }

    // 圖片區域
    private static Image createHeroBlock(String imgurId) {
        var postbackAction = new PostbackAction(
            "Original Image",
            "imgur?id=" + imgurId,
            null
        );
        return Image.builder()
            .url(ImgurUtils.getImgurUri(imgurId, ImgurSize.LARGE))
            .size(Image.ImageSize.FULL_WIDTH)
            .aspectRatio(Image.ImageAspectRatio.R1TO1)
            .aspectMode(Image.ImageAspectMode.Cover)
            .action(postbackAction)
            .build();
    }

    // 按鍵區域
    private static Box createFooterBlock(URI liveMainUrl, URI liveRoomUrl, boolean live, String ig) {
        List<FlexComponent> contents = new ArrayList<>();

        if (live) {
            final Button callAction = Button
                .builder()
                .style(Button.ButtonStyle.PRIMARY)
                .height(Button.ButtonHeight.SMALL)
                .action(new URIAction("直播間", liveRoomUrl, null))
                .build();
            contents.add(callAction);
        }

        final Button websiteAction =
            Button.builder()
                .style(Button.ButtonStyle.LINK)
                .height(Button.ButtonHeight.SMALL)
                .action(new URIAction("個人主頁", liveMainUrl, null))
                .build();
        contents.add(websiteAction);

        if (ig != null) {
            URI igUrl = IgUtils.getIgUri(ig);
            final Button igAction =
                Button.builder()
                    .style(Button.ButtonStyle.LINK)
                    .height(Button.ButtonHeight.SMALL)
                    .action(new URIAction("IG", igUrl, null))
                    .build();
            contents.add(igAction);
        }

        return Box.builder()
            .layout(FlexLayout.VERTICAL)
            .spacing(FlexMarginSize.SM)
            .contents(contents)
            .build();
    }

    // 訊息區域
    private static Box createBodyBlock(String title, boolean live, String lastLiveTime) {
        final Text titleText = Text.builder()
            .text(title)
            .weight(Text.TextWeight.BOLD)
            .size(FlexFontSize.LG)
            .build();

        final Box info = createInfoBox(live, lastLiveTime);

        return Box.builder()
            .layout(FlexLayout.VERTICAL)
            .contents(Arrays.asList(titleText, info))
            .build();
    }

    private static Box createInfoBox(boolean live, String lastLiveTime) {
        String icon = live ? "\uD83D\uDD34️" : "⚪️";
        String status = live ? " 直播中" : " 未開播";
        final Box place = Box
            .builder()
            .layout(FlexLayout.BASELINE)
            .spacing(FlexMarginSize.SM)
            .contents(Arrays.asList(
                Text.builder()
                    .text("目前狀態:")
                    .color("#aaaaaa")
                    .size(FlexFontSize.SM)
                    .flex(2)
                    .build(),
                Text.builder()
                    .text(icon + status)
                    .wrap(true)
                    .color("#666666")
                    .size(FlexFontSize.SM)
                    .flex(5)
                    .build()
            ))
            .build();
        final Box time =
            Box.builder()
                .layout(FlexLayout.BASELINE)
                .spacing(FlexMarginSize.SM)
                .contents(Arrays.asList(
                    Text.builder()
                        .text("上次直播:")
                        .color("#aaaaaa")
                        .size(FlexFontSize.SM)
                        .flex(2)
                        .build(),
                    Text.builder()
                        .text(lastLiveTime)
                        .wrap(true)
                        .color("#666666")
                        .size(FlexFontSize.SM)
                        .flex(5)
                        .build()
                ))
                .build();

        return Box.builder()
            .layout(FlexLayout.VERTICAL)
            .margin(FlexMarginSize.LG)
            .spacing(FlexMarginSize.SM)
            .contents(Arrays.asList(place, time))
            .build();
    }
}
