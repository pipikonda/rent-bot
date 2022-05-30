package com.pipikonda.rentbot.bot.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.pipikonda.rentbot.bot.model.BotAction;
import com.pipikonda.rentbot.bot.model.TelegramApiAction;
import com.pipikonda.rentbot.bot.model.request.markup.ReplyMarkup;
import lombok.Builder;
import lombok.Getter;
import lombok.Value;

@Builder(toBuilder = true)
@Value
public class SendMessageRequest implements TelegramApiAction {

    @JsonProperty("chat_id")
    Long chatId;
    String text;

    @JsonProperty("parse_mode")
    String parseMode; //enum???

    @JsonProperty("disable_web_page_preview")
    boolean disableWebPagePreview;

    @JsonProperty("disable_notification")
    boolean disableNotification;

    @JsonProperty("reply_markup")
    ReplyMarkup replyMarkup;

    @Override
    public String getActionName() {
        return BotAction.SEND_MESSAGE.getCommandName();
    }
}
