package com.pino.line;

import com.linecorp.bot.client.LineMessagingClient;
import com.linecorp.bot.model.PushMessage;
import com.linecorp.bot.model.message.Message;
import com.linecorp.bot.model.message.TextMessage;
import com.pino.exception.InternalServerErrorException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;

@Slf4j
@RequiredArgsConstructor
@Service
public class LineBotService {

    @Value("${line.bot.channel-token}")
    private String lineBotChannelToken;

    public void pushMessage(String userId, String message) {
        pushMessage(userId, new TextMessage(message));
    }

    public void pushMessage(String userId, Message message) {
        final var client = getLineMessagingClient();
        final var pushMessage = new PushMessage(userId, message);
        try {
            client.pushMessage(pushMessage).get();
        } catch (InterruptedException | ExecutionException e) {
            Thread.currentThread().interrupt();
            throw new InternalServerErrorException("pushMessage failed", e);
        }
    }

    public UserProfile getUserProfile(String userId) {
        final var client = getLineMessagingClient();
        try {
            final var userProfileResponse = client.getProfile(userId).get();
            var userProfile = new UserProfile();
            userProfile.setUserId(userProfileResponse.getUserId());
            userProfile.setDisplayName(userProfileResponse.getDisplayName());
            userProfile.setPictureUrl(userProfileResponse.getPictureUrl());
            return userProfile;
        } catch (InterruptedException | ExecutionException e) {
            Thread.currentThread().interrupt();
            throw new InternalServerErrorException("getUserProfile failed", e);
        }
    }

    private LineMessagingClient getLineMessagingClient() {
        return LineMessagingClient
            .builder(lineBotChannelToken)
            .build();
    }

}
