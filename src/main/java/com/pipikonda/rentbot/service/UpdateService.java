package com.pipikonda.rentbot.service;

import com.pipikonda.rentbot.bot.model.update.Update;
import com.pipikonda.rentbot.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class UpdateService {

    private final UserService userService;

    public void processUpdate(Update update) {
        //process update
        if (update.getMessage() != null) {
            //user enter message

        }
        if (update.getMyChatMember() != null) {
            //значит что пользователь подписался или отписался от бота
            log.info("got update getMyChatMember");
            userService.saveUserState(update.getMyChatMember());
        }
    }
}
