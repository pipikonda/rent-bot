package com.pipikonda.rentbot.bot.model.request.markup;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Value;

/**
 * https://core.telegram.org/bots/api#keyboardbutton
 */
@Value
@Builder
public class KeyboardButton {

    String text;

    @JsonProperty("request_contact")
    boolean requestContact;

    @JsonProperty("request_location")
    boolean requestLocation;

    @JsonProperty("web_app")
    WebAppInfo webApp;
}
