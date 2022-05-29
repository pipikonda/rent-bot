package com.pipikonda.rentbot.bot;

import com.pipikonda.rentbot.bot.model.request.SetWebhookRequest;
import com.pipikonda.rentbot.bot.model.TelegramApiAction;
import com.pipikonda.rentbot.bot.service.BotProperties;
import com.pipikonda.rentbot.bot.service.TelegramBotActionClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@RequiredArgsConstructor
public class RentBot {

    private final TelegramBotActionClient telegramBotActionClient;
    private final BotProperties botProperties;

    @PostConstruct
    void initBot() {
        telegramBotActionClient.sendRequest(SetWebhookRequest.builder()
                .url(botProperties.getCallbackUrl())
                .build());
    }

    public void execute(TelegramApiAction telegramApiAction) {
        telegramBotActionClient.sendRequest(telegramApiAction);
    }
}
