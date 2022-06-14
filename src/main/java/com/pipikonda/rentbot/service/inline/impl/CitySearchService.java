package com.pipikonda.rentbot.service.inline.impl;

import com.pipikonda.rentbot.bot.model.request.impl.inline_result.InlineQueryResult;
import com.pipikonda.rentbot.bot.model.request.impl.inline_result.impl.InlineQueryResultArticle;
import com.pipikonda.rentbot.bot.model.request.impl.inline_result.impl.InputTextMessageContent;
import com.pipikonda.rentbot.bot.model.update.InlineQuery;
import com.pipikonda.rentbot.domain.Lang;
import com.pipikonda.rentbot.domain.User;
import com.pipikonda.rentbot.error.BasicLogicException;
import com.pipikonda.rentbot.error.ErrorCode;
import com.pipikonda.rentbot.repository.UserRepository;
import com.pipikonda.rentbot.service.CityService;
import com.pipikonda.rentbot.service.inline.SearchService;
import com.pipikonda.rentbot.service.inline.SearchType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class CitySearchService implements SearchService {

    private static final SearchType type = SearchType.CITY;
    private final UserRepository userRepository;
    private final CityService cityService;

    @Override
    @Transactional
    public List<InlineQueryResult> search(InlineQuery inlineQuery) {
        String query = simplifyQuery(inlineQuery.getQuery());
        if (query.length() < 3) {
            return List.of();
        }
        log.info("CitySearchComponent");
        User user = userRepository.findById(inlineQuery.getFrom().getId())
                .orElseThrow(() -> new BasicLogicException(ErrorCode.NOT_FOUND, "Not found user with id " + inlineQuery.getFrom().getId()));

        return cityService.getCitiesByValue(query)
                        .stream()
                .map(e -> InlineQueryResultArticle.builder()
                        .id(String.valueOf(e.getId()))
                        .title(Optional.ofNullable(e.getNames().get(user.getLang()))
                                .orElse(e.getNames().get(Lang.DEFAULT_LANG)))
                        .inputMessageContent(InputTextMessageContent.builder()
                                .messageText(Optional.ofNullable(e.getNames().get(user.getLang()))
                                        .orElse(e.getNames().get(Lang.DEFAULT_LANG)))
                                .build())
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public SearchType supports() {
        return type;
    }

    private String simplifyQuery(String query) {
        return query.replace(type.name() + " ", "");
    }
}
