package com.pipikonda.rentbot.bot.model.update;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Value;

/**
 https://core.telegram.org/bots/api#update
 */
@Value
@Builder
public class Update {

    @JsonProperty("update_id")
    Long updateId;

    Message message;

    @JsonProperty("inline_query")
    InlineQuery inlineQuery;

    @JsonProperty("my_chat_member")
    ChatMemberUpdate myChatMember;

    @JsonProperty("chat_member")
    ChatMemberUpdate chatMember; //for public chat's

}
