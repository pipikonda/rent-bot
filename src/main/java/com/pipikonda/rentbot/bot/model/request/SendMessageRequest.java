package com.pipikonda.rentbot.bot.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.pipikonda.rentbot.bot.model.TelegramApiAction;
import com.pipikonda.rentbot.bot.model.BotAction;

public class SendMessageRequest extends TelegramApiAction {

    @JsonProperty("chat_id")
    Long chatId;
    String text;

    @JsonProperty("parse_mode")
    String parseMode; //enum???

    @JsonProperty("disable_web_page_preview")
    Boolean disableWebPagePreview;

    @JsonProperty("disable_notification")
    Boolean disableNotification;

    //replyMarkup




    protected SendMessageRequest(BotAction botAction) {
        super(BotAction.SEND_MESSAGE);
    }
}
