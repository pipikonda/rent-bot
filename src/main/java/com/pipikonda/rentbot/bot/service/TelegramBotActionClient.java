package com.pipikonda.rentbot.bot.service;

import com.pipikonda.rentbot.bot.model.TelegramApiAction;
import lombok.extern.slf4j.Slf4j;
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
        log.info("Action queryString is {}", action);
        String requestUrl = botProperties.getTelegramUrl() + action;
        restTemplate.exchange(requestUrl, HttpMethod.GET, null, Object.class);
    }
}
