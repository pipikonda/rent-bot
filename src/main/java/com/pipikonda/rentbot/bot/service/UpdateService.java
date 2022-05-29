package com.pipikonda.rentbot.bot.service;

import com.pipikonda.rentbot.bot.model.update.Update;
import com.pipikonda.rentbot.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdateService {

    private final UserService userService;

    public void processUpdate(Update update) {
        //process update
        if (update.getMyChatMember() != null) {
            //значит что пользователь подписался или отписался от бота
            userService.saveUserState(update.getMyChatMember());
        }
    }
}
