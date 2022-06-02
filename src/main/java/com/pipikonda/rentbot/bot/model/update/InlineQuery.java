package com.pipikonda.rentbot.bot.model.update;

import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class InlineQuery {

    String id;
    UserDto from;
    String query;
    String offset;

    //chat_type
    //location
}
