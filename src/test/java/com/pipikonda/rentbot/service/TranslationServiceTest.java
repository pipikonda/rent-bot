package com.pipikonda.rentbot.service;

import com.pipikonda.rentbot.TestContainersBaseClass;
import com.pipikonda.rentbot.controller.CityController;
import com.pipikonda.rentbot.domain.City;
import com.pipikonda.rentbot.domain.Lang;
import com.pipikonda.rentbot.domain.Translation;
import com.pipikonda.rentbot.domain.TranslationInfo;
import com.pipikonda.rentbot.repository.TranslationInfoRepository;
import com.pipikonda.rentbot.repository.TranslationRepository;
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
class TranslationServiceTest extends TestContainersBaseClass {

    @Autowired
    private TranslationService instance;

    @Autowired
    private TranslationRepository translationRepository;

    @Autowired
    private TranslationInfoRepository translationInfoRepository;

    @Autowired
    private CityService cityService;

    @BeforeEach
    @AfterEach
    void clearDb() {
        translationRepository.deleteAll();
        translationInfoRepository.deleteAll();
    }

    @Test
    void testSaveTranslations() {
        Map<Lang, String> map = Map.of(Lang.UK, " привіт", Lang.RU, "привет ");
        TranslationInfo.TranslationType type = TranslationInfo.TranslationType.CITY_NAME;

        Long translationId = instance.saveTranslations(map, type);

        assertThat(translationInfoRepository.findById(translationId)).isPresent()
                .hasValueSatisfying(e -> assertThat(e).isEqualTo(TranslationInfo.builder()
                        .id(translationId)
                        .translationType(TranslationInfo.TranslationType.CITY_NAME)
                        .build()));

        assertThat(translationRepository.findAll().size()).isEqualTo(2);
    }

    @Test
    @Transactional
    void testGetTranslationsAsMap() {
        TranslationInfo translationInfo = translationInfoRepository.save(TranslationInfo.builder()
                        .translationType(TranslationInfo.TranslationType.CITY_NAME)
                .build());
        translationRepository.save(Translation.builder()
                        .lang(Lang.DEFAULT_LANG)
                        .value("some value default lang")
                        .translationId(translationInfo.getId())
                .build());
        translationRepository.save(Translation.builder()
                .lang(Lang.RU)
                .value("some value another lang")
                .translationId(translationInfo.getId())
                .build());
        translationRepository.save(Translation.builder()
                .lang(Lang.DEFAULT_LANG)
                .value("another value default lang")
                .translationId(78755L)
                .build());

        assertThat(instance.getTranslationsAsMap(translationInfo.getId()))
                .isEqualTo(Map.of(Lang.DEFAULT_LANG, "some value default lang", Lang.RU, "some value another lang"));
    }

    @Test
    void findByValueLike() {
        City city = cityService.create(CityController.CityCreateDto.builder()
                        .names(Map.of(Lang.RU, "Николаев", Lang.UK, "Миколаїв"))
                .build());

        assertThat(instance.findByValueLike("Ник", Set.of(city.getTranslationId())).size())
                .isEqualTo(1);
    }

    @Test
    void findByValueLike_whenInputHasNotMatchValue() {
        assertThat(instance.findByValueLike("Жит", Set.of()))
                .isEqualTo(List.of());
    }
}