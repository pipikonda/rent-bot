package com.pipikonda.rentbot.bot.model.request.impl.markup;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Value;

/**
 * https://core.telegram.org/bots/api#keyboardbutton
 */
@Value
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class KeyboardButton {

    String text;

    @JsonProperty("request_contact")
    Boolean requestContact;

    @JsonProperty("request_location")
    Boolean requestLocation;

    @JsonProperty("web_app")
    WebAppInfo webApp;
}
