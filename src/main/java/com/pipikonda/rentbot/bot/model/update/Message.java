package com.pipikonda.rentbot.bot.model.update;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class Message {

    @JsonProperty("message_id")
    Long messageId;

    @JsonProperty("sender_chat")
    Chat senderChat;

    Long date;
    Chat chat;
    String text;

    //reply markup

}
