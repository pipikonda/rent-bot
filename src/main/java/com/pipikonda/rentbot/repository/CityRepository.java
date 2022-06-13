package com.pipikonda.rentbot.repository;

import com.pipikonda.rentbot.domain.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Repository
public interface CityRepository extends JpaRepository<City, Long> {

    Stream<City> findByTranslationIdIn(Set<Long> translations);

    default Map<Long, Long> findByTranslationsAsMap(Set<Long> translations) {
        if (translations.isEmpty()) {
            return Map.of();
        }
        return findByTranslationIdIn(translations)
                .collect(Collectors.toMap(City::getTranslationId, City::getId));
    }

    default Stream<City> findByTranslationIdList(Set<Long> translations) {
        if (translations.isEmpty()) {
            return Stream.of();
        }
        return findByTranslationIdIn(translations);
    }
}
