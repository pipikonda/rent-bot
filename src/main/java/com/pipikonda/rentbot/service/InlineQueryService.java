package com.pipikonda.rentbot.service;

import com.pipikonda.rentbot.bot.model.request.impl.AnswerInlineQueryRequest;
import com.pipikonda.rentbot.bot.model.request.impl.inline_result.InlineQueryResult;
import com.pipikonda.rentbot.bot.model.update.InlineQuery;
import com.pipikonda.rentbot.error.BasicLogicException;
import com.pipikonda.rentbot.error.ErrorCode;
import com.pipikonda.rentbot.service.inline.SearchFactory;
import com.pipikonda.rentbot.service.inline.SearchType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class InlineQueryService {

    private final SearchFactory searchFactory;


    public AnswerInlineQueryRequest findByInlineQuery(InlineQuery inlineQuery) {
        String query = inlineQuery.getQuery().toUpperCase();
        SearchType searchType = Arrays.stream(SearchType.values())
                .filter(e -> query.startsWith(e.name()))
                .findFirst()
                .orElseThrow(() -> new BasicLogicException(ErrorCode.NOT_FOUND, "Not supported search type"));

        return AnswerInlineQueryRequest.builder()
                .inlineQueryId(inlineQuery.getId())
                .results(searchFactory.getSearchService(searchType).search(inlineQuery))
                .build();
    }
}
