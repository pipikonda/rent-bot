package com.pipikonda.rentbot.service;

import com.pipikonda.rentbot.domain.Lang;
import com.pipikonda.rentbot.domain.Translation;
import com.pipikonda.rentbot.domain.TranslationInfo;
import com.pipikonda.rentbot.repository.TranslationInfoRepository;
import com.pipikonda.rentbot.repository.TranslationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class TranslationService {

    private final TranslationRepository translationRepository;
    private final TranslationInfoRepository translationInfoRepository;

    @Transactional
    public long saveTranslations(Map<Lang, String> names, TranslationInfo.TranslationType type) {
        long translationId = translationInfoRepository.save(TranslationInfo.builder()
                        .translationType(type)
                        .build())
                .getId();
        Optional.ofNullable(names)
                .ifPresent(e -> e.forEach((key, value) -> translationRepository.save(Translation.builder()
                        .translationId(translationId)
                        .lang(key)
                        .value(value.trim())
                        .build())));
        return translationId;
    }

    public Map<Lang, String> getTranslationsAsMap(Long translationId) {
        return translationRepository.findByTranslationId(translationId)
                .collect(Collectors.toMap(Translation::getLang, Translation::getValue));
    }

    public Map<Long, List<Translation>> getTranslationsMap(Set<Long> translations) {
        return translationRepository.findTranslationsByIdSet(translations)
                .collect(Collectors.groupingBy(Translation::getTranslationId));
    }

    @Transactional
    public List<Translation> findByValueLike(String value, Set<Long> translations) {
        log.info("search in {} translations", translations.size());
        if (translations.isEmpty()) {
            return List.of();
        }
        return translationRepository.findTranslationStartWithValue(translations, value).toList();
    }

}
