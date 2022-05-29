package com.pipikonda.rentbot.bot.model;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.Map;

public abstract class TelegramApiAction {

    private final BotAction botAction;

    protected TelegramApiAction(BotAction botAction) {
        this.botAction = botAction;
    }
    
    public String toString() {
        ObjectMapper mapper = new ObjectMapper();
        Map<String, String> requestAsMap = mapper.convertValue(this, Map.class);
        List<String> params = requestAsMap.entrySet().stream()
                .filter(e -> !e.getKey().equals("botAction"))
                .filter(e -> e.getValue() != null)
                .map(e -> e.getKey() + "=" + e.getValue())
                .toList();
        return botAction.getCommandName() + "?" + String.join("&", params);
    }
}
