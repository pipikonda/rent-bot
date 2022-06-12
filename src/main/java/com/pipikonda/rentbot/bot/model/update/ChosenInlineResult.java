package com.pipikonda.rentbot.bot.model.update;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.pipikonda.rentbot.domain.User;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ChosenInlineResult {

    @JsonProperty("result_id")
    String resultId;
    User from;

    @JsonProperty("inline_message_id")
    String inlineMessageId;

    String query;
}
