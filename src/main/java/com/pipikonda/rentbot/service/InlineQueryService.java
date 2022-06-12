package com.pipikonda.rentbot.service;

import com.pipikonda.rentbot.bot.model.request.impl.AnswerInlineQueryRequest;
import com.pipikonda.rentbot.bot.model.request.impl.inline_result.InlineQueryResult;
import com.pipikonda.rentbot.bot.model.request.impl.inline_result.impl.InlineQueryResultLocation;
import com.pipikonda.rentbot.bot.model.update.InlineQuery;
import com.pipikonda.rentbot.error.BasicLogicException;
import com.pipikonda.rentbot.error.ErrorCode;
import com.pipikonda.rentbot.service.http.SearchLocationClient;
import com.pipikonda.rentbot.service.inline.SearchFactory;
import com.pipikonda.rentbot.service.inline.SearchType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class InlineQueryService {

    private final SearchLocationClient searchLocationClient;
    private final SearchFactory searchFactory;

    public AnswerInlineQueryRequest processInlineQuery(InlineQuery inlineQuery) {
        String queryString = inlineQuery.getQuery();

        AnswerInlineQueryRequest answer = AnswerInlineQueryRequest.builder()
                .inlineQueryId(inlineQuery.getId())
                .results(List.of())
                .build();

        if (queryString.length() >= 3) {
            answer = answer.toBuilder()
                    .results(Arrays.stream(searchLocationClient.getCity(inlineQuery.getQuery()))
                            .map(e -> InlineQueryResultLocation.builder()
                                    .id(UUID.randomUUID().toString())
                                    .latitude(e.getLat())
                                    .longitude(e.getLon())
                                    .title(e.getDisplayName())
                                    .build())
                            .collect(Collectors.toList()))
                    .build();
        }
        return answer;
    }

    public AnswerInlineQueryRequest findByInlineQuery(InlineQuery inlineQuery) {
        String query = inlineQuery.getQuery().toUpperCase();
        SearchType searchType = Arrays.stream(SearchType.values()).filter(e -> query.startsWith(e.name()))
                .findFirst()
                .orElseThrow(() -> new BasicLogicException(ErrorCode.NOT_FOUND, "Not supported search type"));

        AnswerInlineQueryRequest answer = AnswerInlineQueryRequest.builder()
                .inlineQueryId(inlineQuery.getId())
                .results(List.of())
                .build();

        List<InlineQueryResult> result = searchFactory.getSearchService(searchType).search(inlineQuery);

        return answer.toBuilder()
                .results(result)
                .build();
    }
}
