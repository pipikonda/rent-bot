package com.pipikonda.rentbot.bot.model.request.impl;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.pipikonda.rentbot.bot.model.enums.BotAction;
import com.pipikonda.rentbot.bot.model.request.TelegramApiAction;
import com.pipikonda.rentbot.bot.model.request.impl.markup.ReplyMarkup;
import lombok.Builder;
import lombok.Value;

@Builder(toBuilder = true)
@Value
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SendMessageRequest implements TelegramApiAction {

    @JsonProperty("chat_id")
    Long chatId;
    String text;

    @JsonProperty("parse_mode")
    String parseMode;

    @JsonProperty("disable_web_page_preview")
    Boolean disableWebPagePreview;

    @JsonProperty("disable_notification")
    Boolean disableNotification;

    @JsonProperty("reply_markup")
    ReplyMarkup replyMarkup;

    @Override
    public String getActionName() {
        return BotAction.SEND_MESSAGE.getCommandName();
    }
}
