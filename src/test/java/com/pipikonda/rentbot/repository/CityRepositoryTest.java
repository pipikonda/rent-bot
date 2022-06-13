package com.pipikonda.rentbot.repository;

import com.pipikonda.rentbot.TestContainersBaseClass;
import com.pipikonda.rentbot.domain.City;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class CityRepositoryTest extends TestContainersBaseClass {

    @Autowired
    private CityRepository instance;

    @BeforeEach
    @AfterEach
    void clearDb() {
        instance.deleteAll();
    }

    @Test
    @Transactional
    void testSave() {
        City city = instance.save(City.builder()
                        .translationId(24L)
                .build());

        assertThat(city.getId()).isNotNull();
        assertThat(instance.findById(city.getId())).isPresent();
    }

    @Test
    @Transactional
    void testFindByTranslationIdIn() {
        City city1 = instance.save(City.builder()
                .translationId(24L)
                .build());
        City city2 = instance.save(City.builder()
                .translationId(27L)
                .build());
        City city3 = instance.save(City.builder()
                .translationId(1452L)
                .build());

        assertThat(instance.findByTranslationIdIn(Set.of(24L, 27L)).toList())
                .isEqualTo(List.of(city1, city2));

        assertThat(instance.findByTranslationIdIn(Set.of(447L))).isEmpty();
    }

    @Test
    @Transactional
    void testFindByTranslationsAsMap_shouldReturnEmptyMap_whenInputIsEmpty() {
        assertThat(instance.findByTranslationsAsMap(Set.of())).isEqualTo(Map.of());
    }

    @Test
    @Transactional
    void testFindByTranslationsAsMap() {
        City city1 = instance.save(City.builder()
                .translationId(24L)
                .build());
        City city2 = instance.save(City.builder()
                .translationId(27L)
                .build());
        City city3 = instance.save(City.builder()
                .translationId(1452L)
                .build());

        assertThat(instance.findByTranslationsAsMap(Set.of(24L, 1452L)))
                .isEqualTo(Map.of(24L, city1.getId(), 1452L, city3.getId()));
    }
}
