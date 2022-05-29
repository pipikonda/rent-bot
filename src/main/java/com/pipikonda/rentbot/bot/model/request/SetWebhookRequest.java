package com.pipikonda.rentbot.bot.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.pipikonda.rentbot.bot.model.BotAction;
import com.pipikonda.rentbot.bot.model.TelegramApiAction;
import lombok.Builder;
import lombok.Getter;

@Builder(toBuilder = true)
@Getter
public class SetWebhookRequest extends TelegramApiAction {

    @JsonProperty("url")
    private final String url;

    @JsonProperty("drop_pending_updates")
    private final Boolean dropPendingUpdates;

    public SetWebhookRequest(String url, Boolean dropPendingUpdates) {
        super(BotAction.SET_WEBHOOK);
        this.url = url;
        this.dropPendingUpdates = dropPendingUpdates;
    }
}
