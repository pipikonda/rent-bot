package com.pipikonda.rentbot.bot.model.update;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class ChatMemberUpdate {

    Chat chat;
    UserDto from;
    Long date;

    @JsonProperty("old_chat_member")
    ChatMember oldChatMember;

    @JsonProperty("new_chat_member")
    ChatMember newChatMember;

    @JsonProperty("invite_link")
    String inviteLink;
}
