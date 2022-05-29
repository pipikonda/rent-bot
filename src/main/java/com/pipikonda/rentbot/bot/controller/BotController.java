package com.pipikonda.rentbot.bot.controller;

import com.pipikonda.rentbot.bot.model.update.Update;
import com.pipikonda.rentbot.bot.service.UpdateService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class BotController {

    private final UpdateService updateService;

    @PostMapping("/callback/update")
    public void updateCallback(@RequestBody Update update) {
        log.info("Get update {}", update.getUpdateId());
        updateService.processUpdate(update);

    }
}
