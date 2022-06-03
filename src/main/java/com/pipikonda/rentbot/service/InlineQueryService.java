package com.pipikonda.rentbot.service;

import com.pipikonda.rentbot.bot.RentBot;
import com.pipikonda.rentbot.bot.model.request.impl.AnswerInlineQueryRequest;
import com.pipikonda.rentbot.bot.model.request.impl.inline_result.InlineQueryResult;
import com.pipikonda.rentbot.bot.model.request.impl.inline_result.impl.InlineQueryResultLocation;
import com.pipikonda.rentbot.bot.model.update.InlineQuery;
import com.pipikonda.rentbot.service.http.SearchLocationClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class InlineQueryService {

    private final RentBot rentBot;
    private final SearchLocationClient searchLocationClient;

    public void processInlineQuery(InlineQuery inlineQuery) {
        String queryString = inlineQuery.getQuery();
        AnswerInlineQueryRequest answer = AnswerInlineQueryRequest.builder()
                .inlineQueryId(inlineQuery.getId())
                .results(List.of())
                .build();
        if (queryString.length() < 3) {
            rentBot.execute(answer);
            return;
        }
        List<InlineQueryResult> cities = Arrays.stream(searchLocationClient.getCity(inlineQuery.getQuery()))
                .map(e -> InlineQueryResultLocation.builder()
                        .id(UUID.randomUUID() + "_" + inlineQuery.getId())
                        .latitude(e.getLat())
                        .longitude(e.getLon())
                        .title(e.getDisplayName())
                        .build())
                .collect(Collectors.toList());

        rentBot.execute(answer.toBuilder()
                .results(cities)
                .build());
    }
}
