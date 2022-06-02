package com.pipikonda.rentbot.bot.service;

import com.pipikonda.rentbot.bot.model.request.TelegramApiAction;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Component
public class TelegramBotActionClient {

    private final RestTemplate restTemplate;
    private final BotProperties botProperties;

    public TelegramBotActionClient(RestTemplate restTemplate,
                                   BotProperties botProperties) {
        this.restTemplate = restTemplate;
        this.botProperties = botProperties;
    }

    public void sendRequest(TelegramApiAction action) {
        String requestUrl = botProperties.getTelegramUrl() + action.getActionName();
        restTemplate.exchange(requestUrl, HttpMethod.POST, new HttpEntity<>(action), Object.class);
    }
}
