package com.pipikonda.rentbot.service;

import com.pipikonda.rentbot.TestContainersBaseClass;
import com.pipikonda.rentbot.controller.CityController;
import com.pipikonda.rentbot.domain.City;
import com.pipikonda.rentbot.domain.Lang;
import com.pipikonda.rentbot.domain.Translation;
import com.pipikonda.rentbot.domain.TranslationInfo;
import com.pipikonda.rentbot.error.BasicLogicException;
import com.pipikonda.rentbot.repository.CityRepository;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

@SpringBootTest
class CityServiceTest extends TestContainersBaseClass {

    @Autowired
    private CityService instance;

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private TranslationRepository translationRepository;

    @Autowired
    private TranslationInfoRepository translationInfoRepository;

    @BeforeEach
    @AfterEach
    void clearDb() {
        cityRepository.deleteAll();
        translationRepository.deleteAll();
        translationInfoRepository.deleteAll();
    }

    @Test
    @Transactional
    void testCreate() {
        City actualResult = instance.create(CityController.CityCreateDto.builder()
                .names(Map.of(Lang.UK, " ім'я", Lang.RU, "имя "))
                .build());

        assertThat(translationInfoRepository.findById(actualResult.getTranslationId())).isPresent()
                .hasValueSatisfying(e -> assertThat(e.getTranslationType()).isEqualTo(TranslationInfo.TranslationType.CITY_NAME));
        assertThat(translationRepository.findByTranslationId(actualResult.getTranslationId()).toList().size())
                .isEqualTo(2);

    }

    @Test
    void testGetList_shouldReturnEmptyList_whenCityIsNotExists() {
        assertThat(instance.getList()).isEqualTo(List.of());
    }

    @Test
    @Transactional
    void testGetList_shouldReturnList_whenCitiesArePresent() {
        TranslationInfo translationInfo = translationInfoRepository.save(TranslationInfo.builder()
                .translationType(TranslationInfo.TranslationType.CITY_NAME)
                .build());

        Translation translation1 = translationRepository.save(Translation.builder()
                .translationId(translationInfo.getId())
                .lang(Lang.UK)
                .value("ua name")
                .build());
        Translation translation2 = translationRepository.save(Translation.builder()
                .translationId(translationInfo.getId())
                .lang(Lang.RU)
                .value("ru name")
                .build());

        City city = cityRepository.save(City.builder()
                .translationId(translationInfo.getId())
                .build());

        assertThat(instance.getList())
                .isEqualTo(List.of(CityController.CityDto.builder()
                        .id(city.getId())
                        .names(Map.of(Lang.UK, "ua name", Lang.RU, "ru name"))
                        .build()));
    }

    @Test
    void testFindById_shouldThrowException_whenEntityIsNotExists() {
        assertThatExceptionOfType(BasicLogicException.class)
                .isThrownBy(() -> instance.findById(12L));
    }

    @Test
    @Transactional
    void testFindById() {
        TranslationInfo translationInfo = translationInfoRepository.save(TranslationInfo.builder()
                .translationType(TranslationInfo.TranslationType.CITY_NAME)
                .build());

        Translation translation1 = translationRepository.save(Translation.builder()
                .translationId(translationInfo.getId())
                .lang(Lang.UK)
                .value("ua name")
                .build());
        Translation translation2 = translationRepository.save(Translation.builder()
                .translationId(translationInfo.getId())
                .lang(Lang.RU)
                .value("ru name")
                .build());

        City city = cityRepository.save(City.builder()
                .translationId(translationInfo.getId())
                .build());

        assertThat(instance.findById(city.getId()))
                .isEqualTo(CityController.CityDto.builder()
                        .id(city.getId())
                        .names(Map.of(Lang.UK, "ua name", Lang.RU, "ru name"))
                        .build());
    }

}