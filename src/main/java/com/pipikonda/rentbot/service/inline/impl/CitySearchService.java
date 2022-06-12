package com.pipikonda.rentbot.service.inline.impl;

import com.pipikonda.rentbot.bot.model.request.impl.inline_result.InlineQueryResult;
import com.pipikonda.rentbot.bot.model.request.impl.inline_result.impl.InlineQueryResultArticle;
import com.pipikonda.rentbot.bot.model.update.InlineQuery;
import com.pipikonda.rentbot.domain.Lang;
import com.pipikonda.rentbot.domain.Translation;
import com.pipikonda.rentbot.domain.TranslationInfo;
import com.pipikonda.rentbot.domain.User;
import com.pipikonda.rentbot.error.BasicLogicException;
import com.pipikonda.rentbot.error.ErrorCode;
import com.pipikonda.rentbot.repository.UserRepository;
import com.pipikonda.rentbot.service.CityService;
import com.pipikonda.rentbot.service.TranslationService;
import com.pipikonda.rentbot.service.inline.SearchService;
import com.pipikonda.rentbot.service.inline.SearchType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class CitySearchService implements SearchService {

    private static final SearchType type = SearchType.CITY;
    private final TranslationService translationService;
    private final UserRepository userRepository;
    private final CityService cityService;

    @Override
    @Transactional
    public List<InlineQueryResult> search(InlineQuery inlineQuery) {
        String query = simplifyQuery(inlineQuery.getQuery());
        log.info("CitySearchComponent");
        User user = userRepository.findById(inlineQuery.getFrom().getId())
                .orElseThrow(() -> new BasicLogicException(ErrorCode.NOT_FOUND, "Not found user with id " + inlineQuery.getFrom().getId()));

        Set<Long> translations = translationService.findByValueLike(query, TranslationInfo.TranslationType.CITY_NAME)
                .stream()
                .map(Translation::getTranslationId)
                .collect(Collectors.toSet());

        return cityService.findByTranslationsId(translations).stream()
                .map(e -> InlineQueryResultArticle.builder()
                        .id(String.valueOf(e.getId()))
                        .title(Optional.ofNullable(e.getNames().get(user.getLang()))
                                .orElse(e.getNames().get(Lang.DEFAULT_LANG)))
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
