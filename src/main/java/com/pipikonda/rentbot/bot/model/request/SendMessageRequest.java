package com.pipikonda.rentbot.bot.model.request;

import com.pipikonda.rentbot.bot.model.TelegramApiAction;
import com.pipikonda.rentbot.bot.model.BotAction;

public class SendMessageRequest extends TelegramApiAction {

    protected SendMessageRequest(BotAction botAction) {
        super(BotAction.SEND_MESSAGE);
    }
}
