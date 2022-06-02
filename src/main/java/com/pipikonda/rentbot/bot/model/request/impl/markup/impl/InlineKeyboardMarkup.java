package com.pipikonda.rentbot.bot.model.request.impl.markup.impl;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.pipikonda.rentbot.bot.model.request.impl.markup.InlineKeyboardButton;
import com.pipikonda.rentbot.bot.model.request.impl.markup.ReplyMarkup;
import lombok.Builder;
import lombok.Value;

import java.util.List;

@Builder
@Value
public class InlineKeyboardMarkup implements ReplyMarkup {

    @JsonProperty("inline_keyboard")
    List<List<InlineKeyboardButton>> keyboard;
}
