package com.pipikonda.rentbot.bot.model.request.markup.impl;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.pipikonda.rentbot.bot.model.request.markup.KeyboardButton;
import com.pipikonda.rentbot.bot.model.request.markup.ReplyMarkup;
import lombok.Builder;
import lombok.Value;

import java.util.List;

/**
 * https://core.telegram.org/bots/api#replykeyboardmarkup
 */
@Value
@Builder
public class ReplyKeyboardMarkup implements ReplyMarkup {

    List<List<KeyboardButton>> keyboard;

    @JsonProperty("resize_keyboard")
    boolean resizeKeyboard;

    @JsonProperty("one_time_keyboard")
    boolean oneTimeKeyboard;
//
//    @JsonProperty("input_field_placeholder")
//    String inputFieldPlaceholder;

    boolean selective;
}
