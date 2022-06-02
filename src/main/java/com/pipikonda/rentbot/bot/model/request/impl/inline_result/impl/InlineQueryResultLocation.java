package com.pipikonda.rentbot.bot.model.request.impl.inline_result.impl;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.pipikonda.rentbot.bot.model.enums.InlineQueryType;
import com.pipikonda.rentbot.bot.model.request.impl.inline_result.InlineQueryResult;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class InlineQueryResultLocation implements InlineQueryResult {

    InlineQueryType type = InlineQueryType.LOCATION;
    String id;
    Float latitude;
    Float longitude;
    String title;

    @JsonProperty("horizontal_accuracy")
    Float horizontalAccuracy;

    @JsonProperty("live_period")
    Integer livePeriod;

//    @JsonProperty("input_message_content")

}
