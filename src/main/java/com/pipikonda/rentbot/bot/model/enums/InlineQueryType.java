package com.pipikonda.rentbot.bot.model.enums;

public enum InlineQueryType {

    LOCATION,
    ARTICLE;

    @Override
    public String toString() {
        return name().toLowerCase();
    }
}
