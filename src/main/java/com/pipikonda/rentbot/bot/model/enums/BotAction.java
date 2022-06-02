package com.pipikonda.rentbot.bot.model.enums;

import lombok.Getter;

@Getter
public enum BotAction {

    SET_WEBHOOK("setWebhook"),
    SEND_MESSAGE("sendMessage"),
    ANSWER_INLINE_QUERY("answerInlineQuery");

    private final String commandName;

    BotAction(String commandName) {
        this.commandName = commandName;
    }
}
