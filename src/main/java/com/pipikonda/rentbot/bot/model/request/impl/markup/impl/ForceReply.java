package com.pipikonda.rentbot.bot.model.request.impl.markup.impl;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.pipikonda.rentbot.bot.model.request.impl.markup.ReplyMarkup;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ForceReply implements ReplyMarkup {

    @JsonProperty("force_reply")
    Boolean replyForce;

    @JsonProperty("input_field_placeholder")
    String inputFieldPlaceholder;

    Boolean selective;
}
