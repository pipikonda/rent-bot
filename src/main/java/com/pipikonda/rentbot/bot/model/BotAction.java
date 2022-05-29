package com.pipikonda.rentbot.bot.model;

import lombok.Getter;

@Getter
public enum BotAction {

    SET_WEBHOOK("setWebhook"),
    SEND_MESSAGE("sendMessage");

    private final String commandName;

    BotAction(String commandName) {
        this.commandName = commandName;
    }
}
