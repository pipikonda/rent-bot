package com.pipikonda.rentbot.bot.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

public abstract class TelegramApiAction {

    @JsonIgnore
    private final BotAction botAction;

    protected TelegramApiAction(BotAction botAction) {
        this.botAction = botAction;
    }
    
    public String toString() {
        return botAction.getCommandName();
    }
}
