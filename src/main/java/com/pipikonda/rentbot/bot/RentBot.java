package com.pipikonda.rentbot.bot;

import com.pipikonda.rentbot.bot.model.request.SetWebhookRequest;
import com.pipikonda.rentbot.bot.model.TelegramApiAction;
import com.pipikonda.rentbot.bot.service.BotProperties;
import com.pipikonda.rentbot.bot.service.TelegramBotActionClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@RequiredArgsConstructor
@Slf4j
public class RentBot {

    private final TelegramBotActionClient telegramBotActionClient;
    private final BotProperties botProperties;

    @PostConstruct
    void initBot() {
        if (botProperties.isSetWebhookPostConstruct()) {
            log.info("telegram.bot.set-webhook-post-construct is true");
            telegramBotActionClient.sendRequest(SetWebhookRequest.builder()
                    .url(botProperties.getCallbackUrl())
                    .build());
        } else {
            log.info("telegram.bot.set-webhook-post-construct is false");
        }
    }

    public void execute(TelegramApiAction telegramApiAction) {
        telegramBotActionClient.sendRequest(telegramApiAction);
    }
}
