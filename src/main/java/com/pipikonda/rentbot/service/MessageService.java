package com.pipikonda.rentbot.service;

import com.pipikonda.rentbot.bot.model.request.impl.SendMessageRequest;
import com.pipikonda.rentbot.bot.model.request.impl.markup.InlineKeyboardButton;
import com.pipikonda.rentbot.bot.model.request.impl.markup.impl.InlineKeyboardMarkup;
import com.pipikonda.rentbot.domain.Lang;
import com.pipikonda.rentbot.domain.User;
import com.pipikonda.rentbot.service.inline.SearchType;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;

@Service
@RequiredArgsConstructor
public class MessageService {

    private final MessageSource messageSource;

    public SendMessageRequest getGreetingMessage(User user, String pattern) {
        return SendMessageRequest.builder()
                .chatId(user.getId())
                .text(messageSource.getMessage(pattern, null, new Locale(user.getLang().name())))
                .replyMarkup(InlineKeyboardMarkup.builder()
                        .keyboard(List.of(
                                List.of(InlineKeyboardButton.builder()
                                                .switchInlineQueryCurrentChat(" " + SearchType.CITY.name() + " ")
                                                .text(messageSource.getMessage("telegram.choice-city", null, new Locale(user.getLang().name())))
                                        .build())
                        ))
                        .build())
                .build();
    }
}
