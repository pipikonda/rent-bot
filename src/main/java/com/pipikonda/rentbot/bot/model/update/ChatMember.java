package com.pipikonda.rentbot.bot.model.update;

import lombok.Builder;
import lombok.Value;

/**
 * https://core.telegram.org/bots/api#chatmember
 */
@Builder
@Value
public class ChatMember {

    UserDto user;
    String status;

}
