package com.pino.restapi.line_bot;

import com.linecorp.bot.model.event.Event;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.PostbackEvent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.message.Message;
import com.linecorp.bot.spring.boot.annotation.EventMapping;
import com.linecorp.bot.spring.boot.annotation.LineMessageHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
@LineMessageHandler
public class DefaultLineMessageHandler {

    private final LineBotReplyService lineBotReplyService;

    @SuppressWarnings("unused")
    @EventMapping
    public Message handleTextMessageEvent(MessageEvent<TextMessageContent> event) {
        final var userId = event.getSource().getUserId();
        final var originalMessageText = event.getMessage().getText();
        return lineBotReplyService.replyTextMessageEvent(userId, originalMessageText);
    }

    @SuppressWarnings("unused")
    @EventMapping
    public Message handlePostbackEvent(PostbackEvent event) {
        log.info("handlePostbackEvent: {}", event);
        return lineBotReplyService.replyPostbackEvent(
            event.getSource().getUserId(),
            event.getPostbackContent().getData()
        );
    }

    @SuppressWarnings("unused")
    @EventMapping
    public void handleDefaultMessageEvent(Event event) {
        log.info("handleDefaultMessageEvent: {}", event);
    }
}
