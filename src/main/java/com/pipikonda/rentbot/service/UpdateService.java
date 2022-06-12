package com.pipikonda.rentbot.service;

import com.pipikonda.rentbot.bot.RentBot;
import com.pipikonda.rentbot.bot.model.request.impl.AnswerInlineQueryRequest;
import com.pipikonda.rentbot.bot.model.update.Update;
import com.pipikonda.rentbot.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class UpdateService {

    private final UserService userService;
    private final InlineQueryService inlineQueryService;
    private final MessageService messageService;
    private final RentBot rentBot;

    public void processUpdate(Update update) {
        //process update
        System.out.println("got update ==> " + update);
        if (update.getMessage() != null) {
            //user enter message

        }

        if (update.getChosenInlineResult() != null) {
            //process Update
        }

        if (update.getInlineQuery() != null) {
            //inline query - поиск города
            //пользователь ввел @bot_name {some_text}
//            AnswerInlineQueryRequest request = inlineQueryService.processInlineQuery(update.getInlineQuery());
            AnswerInlineQueryRequest request = inlineQueryService.findByInlineQuery(update.getInlineQuery());
            rentBot.execute(request);
        }

        if (update.getMyChatMember() != null) {
            //значит что пользователь подписался или отписался от бота
            log.info("got update getMyChatMember from {} {}", update.getMyChatMember().getFrom().getFirstName(),
                    update.getMyChatMember().getFrom().getLastName());
           log.info("language_code is {}",  update.getMyChatMember().getFrom().getLanguageCode());
            User user = userService.saveUserState(update.getMyChatMember());
            if (user.getState().equals(User.UserState.SUBSCRIBED)) {
                rentBot.execute(messageService.getGreetingMessage(user, "telegram.greeting-message"));
            }
        }
    }
}
