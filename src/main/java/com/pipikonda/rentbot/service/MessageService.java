package com.pipikonda.rentbot.service;

import com.pipikonda.rentbot.bot.model.request.SendMessageRequest;
import com.pipikonda.rentbot.bot.model.request.markup.KeyboardButton;
import com.pipikonda.rentbot.bot.model.request.markup.WebAppInfo;
import com.pipikonda.rentbot.bot.model.request.markup.impl.ReplyKeyboardMarkup;
import com.pipikonda.rentbot.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MessageService {

    private final MessageSource messageSource;

    public SendMessageRequest getGreetingMessage(User user) {
        return SendMessageRequest.builder()
                .chatId(user.getId())
                .text("hello guy")
                .parseMode("HTML")
                .replyMarkup(ReplyKeyboardMarkup.builder()
                        .keyboard(
                                List.of(
                                        List.of(KeyboardButton.builder()
                                                .text("HELLO " + user.getUsername())
                                                .webApp(WebAppInfo.builder()
                                                        .url("https://google.com")
                                                        .build())
                                                .build())
                                )
                        )
                        .build())
                .build();
    }

}
