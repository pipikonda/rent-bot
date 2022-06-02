package com.pipikonda.rentbot.bot.model.request.impl.inline_result.impl;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.pipikonda.rentbot.bot.model.request.impl.inline_result.InputMessageContent;
import lombok.Builder;
import lombok.Value;

@Builder
@Value
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class InputTextMessageContent implements InputMessageContent {

    @JsonProperty("message_text")
    String messageText;

    @JsonProperty("parse_mode")
    String parseMode;
}
