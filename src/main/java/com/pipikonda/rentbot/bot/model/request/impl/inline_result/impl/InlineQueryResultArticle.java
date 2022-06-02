package com.pipikonda.rentbot.bot.model.request.impl.inline_result.impl;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.pipikonda.rentbot.bot.model.enums.InlineQueryType;
import com.pipikonda.rentbot.bot.model.request.impl.inline_result.InlineQueryResult;
import com.pipikonda.rentbot.bot.model.request.impl.inline_result.InputMessageContent;
import lombok.Builder;
import lombok.Value;

@Builder
@Value
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class InlineQueryResultArticle implements InlineQueryResult {

    InlineQueryType type = InlineQueryType.ARTICLE;
    String id;
    String title;

    @JsonProperty("input_message_content")
    InputMessageContent inputMessageContent;
}
