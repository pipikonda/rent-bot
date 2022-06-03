package com.pipikonda.rentbot.bot.model.request.impl;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.pipikonda.rentbot.bot.model.enums.BotAction;
import com.pipikonda.rentbot.bot.model.request.TelegramApiAction;
import com.pipikonda.rentbot.bot.model.request.impl.inline_result.InlineQueryResult;
import lombok.Builder;
import lombok.Value;

import java.util.List;

@Builder(toBuilder = true)
@Value
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class AnswerInlineQueryRequest implements TelegramApiAction {

    @JsonProperty("inline_query_id")
    String inlineQueryId;

    List<InlineQueryResult> results;

    @Override
    public String getActionName() {
        return BotAction.ANSWER_INLINE_QUERY.getCommandName();
    }
}
