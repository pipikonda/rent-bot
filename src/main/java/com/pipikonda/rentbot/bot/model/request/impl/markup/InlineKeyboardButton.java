package com.pipikonda.rentbot.bot.model.request.impl.markup;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Value;

/**
https://core.telegram.org/bots/api#inlinekeyboardbutton
 */
@Builder
@Value
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class InlineKeyboardButton {

    String text;
    String url;

    @JsonProperty("callback_data")
    String callbackData;

    @JsonProperty("web_app")
    WebAppInfo webApp;

    //login url

    @JsonProperty("switch_inline_query_current_chat")
    String switchInlineQueryCurrentChat;
}
