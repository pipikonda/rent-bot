package com.pipikonda.rentbot.service;

import com.pipikonda.rentbot.bot.RentBot;
import com.pipikonda.rentbot.bot.model.request.impl.AnswerInlineQueryRequest;
import com.pipikonda.rentbot.bot.model.request.impl.inline_result.impl.InlineQueryResultLocation;
import com.pipikonda.rentbot.bot.model.update.InlineQuery;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class InlineQueryService {

    private final RentBot rentBot;

    public void processInlineQuery(InlineQuery inlineQuery) {
        rentBot.execute(AnswerInlineQueryRequest.builder()
                .inlineQueryId(inlineQuery.getId())
                .results(List.of(InlineQueryResultLocation.builder()
                        .id(UUID.randomUUID().toString())
                        .title("hello search result is " + inlineQuery.getQuery())
                        .latitude((float) 46.6345)
                        .longitude((float) 32.6169)
                        .build()))
                .build());
    }
}
