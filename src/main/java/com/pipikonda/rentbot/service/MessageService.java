package com.pipikonda.rentbot.service;

import com.pipikonda.rentbot.bot.model.request.impl.SendMessageRequest;
import com.pipikonda.rentbot.bot.model.request.impl.markup.InlineKeyboardButton;
import com.pipikonda.rentbot.bot.model.request.impl.markup.impl.InlineKeyboardMarkup;
import com.pipikonda.rentbot.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MessageService {

    private final MessageSource messageSource;

    public SendMessageRequest getTextMessage(User user, String text) {
        return SendMessageRequest.builder()
                .chatId(user.getId())
                .text(text)
                .replyMarkup(InlineKeyboardMarkup.builder()
                        .keyboard(List.of(
                                List.of(InlineKeyboardButton.builder()
                                                .switchInlineQueryCurrentChat(" ")
                                                .text("Выбрать город")
                                        .build())
                        ))
                        .build())
                .build();
    }

}
