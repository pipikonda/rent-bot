package com.pipikonda.rentbot.bot.model.update;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class UserDto {

    Long id;

    @JsonProperty("is_bot")
    Boolean isBot;

    @JsonProperty("first_name")
    String firstName;

    @JsonProperty("last_name")
    String lastName;

    String username;

    @JsonProperty("language_code")
    String languageCode;
}
