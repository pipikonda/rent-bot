package com.pipikonda.rentbot.bot.model.request.impl;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.pipikonda.rentbot.bot.model.enums.BotAction;
import com.pipikonda.rentbot.bot.model.request.TelegramApiAction;
import lombok.Builder;
import lombok.Value;

@Builder(toBuilder = true)
@Value
public class SetWebhookRequest implements TelegramApiAction {

    @JsonProperty("url")
    String url;

    @JsonProperty("drop_pending_updates")
    boolean dropPendingUpdates;

    @Override
    public String getActionName() {
        return BotAction.SET_WEBHOOK.getCommandName();
    }
}
