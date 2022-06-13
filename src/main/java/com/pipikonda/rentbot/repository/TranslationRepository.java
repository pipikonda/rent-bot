package com.pipikonda.rentbot.repository;

import com.pipikonda.rentbot.domain.Translation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

@Repository
public interface TranslationRepository extends JpaRepository<Translation, Long> {

    String MULTIPLY_CHARACTERS = "%";

    Stream<Translation> findByTranslationId(long translationId);

    Stream<Translation> findByTranslationIdIn(Set<Long> translations);

    @Query(value = "select * from translation where translation_id in :translationId and value like :valuePattern", nativeQuery = true)
    Stream<Translation> findByTranslationsAndValue(@Param("translationId") Set<Long> translations,
                                                       @Param("valuePattern") String valuePattern);

    default Stream<Translation> findTranslationStartWithValue(Set<Long> translations, String value) {
        if (translations.isEmpty()) {
            return Stream.of();
        }
        return findByTranslationsAndValue(translations, value + MULTIPLY_CHARACTERS);
    }

    default Stream<Translation> findTranslationsByIdSet(Set<Long> translations) {
        if (translations.isEmpty()) {
            return Stream.empty();
        }
        return findByTranslationIdIn(translations);
    }
}
