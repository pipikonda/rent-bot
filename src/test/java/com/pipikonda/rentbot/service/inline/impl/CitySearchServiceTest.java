package com.pipikonda.rentbot.service.inline.impl;

import com.pipikonda.rentbot.TestContainersBaseClass;
import com.pipikonda.rentbot.bot.model.request.impl.inline_result.impl.InlineQueryResultArticle;
import com.pipikonda.rentbot.bot.model.request.impl.inline_result.impl.InputTextMessageContent;
import com.pipikonda.rentbot.bot.model.update.InlineQuery;
import com.pipikonda.rentbot.bot.model.update.UserDto;
import com.pipikonda.rentbot.controller.CityController;
import com.pipikonda.rentbot.domain.City;
import com.pipikonda.rentbot.domain.Lang;
import com.pipikonda.rentbot.domain.User;
import com.pipikonda.rentbot.error.BasicLogicException;
import com.pipikonda.rentbot.repository.CityRepository;
import com.pipikonda.rentbot.repository.TranslationInfoRepository;
import com.pipikonda.rentbot.repository.TranslationRepository;
import com.pipikonda.rentbot.repository.UserRepository;
import com.pipikonda.rentbot.service.CityService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.CacheManager;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

@SpringBootTest
class CitySearchServiceTest extends TestContainersBaseClass {

    @Autowired
    private CitySearchService instance;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CityService cityService;

    @Autowired
    private TranslationRepository translationRepository;

    @Autowired
    private TranslationInfoRepository translationInfoRepository;

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private CacheManager cacheManager;

    @BeforeEach
    @AfterEach
    void clearDb() {
        cityRepository.deleteAll();
        translationInfoRepository.deleteAll();
        translationRepository.deleteAll();
        userRepository.deleteAll();
        Objects.requireNonNull(cacheManager.getCache("CITY_NAME")).clear();
    }

    @Test
    void testSearch_shouldThrowException_whenUserIsNotExists() {
        UserDto user = UserDto.builder()
                .id(123L)
                .firstName("some name")
                .lastName("some last name")
                .build();
        InlineQuery query = InlineQuery.builder()
                .query("CITY city")
                .from(user)
                .build();

        assertThatExceptionOfType(BasicLogicException.class).isThrownBy(() -> instance.search(query));
    }

    @Test
    void testSearch_shouldReturnEmptyList_whenValueIsNotFound() {
        UserDto userDto = UserDto.builder()
                .id(123L)
                .firstName("some name")
                .lastName("some last name")
                .build();
        InlineQuery query = InlineQuery.builder()
                .query("CITY ??????")
                .from(userDto)
                .build();

        User user = userRepository.save(User.builder()
                .lang(Lang.DEFAULT_LANG)
                .id(123L)
                .build());

        assertThat(instance.search(query)).isEqualTo(List.of());
    }

    @Test
    void testSearch_shouldReturnResult_whenUserLangIsEqualToValueLang() {
        UserDto userDto = UserDto.builder()
                .id(123L)
                .firstName("some name")
                .lastName("some last name")
                .build();
        InlineQuery query = InlineQuery.builder()
                .query("CITY ??????")
                .from(userDto)
                .build();

        User user = userRepository.save(User.builder()
                .lang(Lang.RU)
                .id(123L)
                .build());

        City city1 = cityService.create(CityController.CityCreateDto.builder()
                .names(Map.of(Lang.UK, "????????????????", Lang.RU, "????????????????"))
                .build());

        assertThat(instance.search(query)).isEqualTo(List.of(InlineQueryResultArticle.builder()
                .id(String.valueOf(city1.getId()))
                .title("????????????????")
                .inputMessageContent(InputTextMessageContent.builder()
                        .messageText("????????????????")
                        .build())
                .build()));
    }

    @Test
    void testSearch_shouldReturnValueAtAnotherLang_whenUserLangIsNotEqualValueLang() {
        UserDto userDto = UserDto.builder()
                .id(123L)
                .firstName("some name")
                .lastName("some last name")
                .build();
        InlineQuery query = InlineQuery.builder()
                .query("CITY ??????")
                .from(userDto)
                .build();

        User user = userRepository.save(User.builder()
                .lang(Lang.UK)
                .id(123L)
                .build());

        City city1 = cityService.create(CityController.CityCreateDto.builder()
                .names(Map.of(Lang.UK, "????????????????", Lang.RU, "????????????????"))
                .build());

        assertThat(instance.search(query)).isEqualTo(List.of(InlineQueryResultArticle.builder()
                .id(String.valueOf(city1.getId()))
                .title("????????????????")
                .inputMessageContent(InputTextMessageContent.builder()
                        .messageText("????????????????")
                        .build())
                .build()));
    }

    @Test
    void testSearch_shouldReturnValueAtDefaultLang_whenUserLangHasNotValue() {
        UserDto userDto = UserDto.builder()
                .id(123L)
                .firstName("some name")
                .lastName("some last name")
                .build();
        InlineQuery query = InlineQuery.builder()
                .query("CITY ??????")
                .from(userDto)
                .build();

        User user = userRepository.save(User.builder()
                .lang(Lang.RU)
                .id(123L)
                .build());

        City city1 = cityService.create(CityController.CityCreateDto.builder()
                .names(Map.of(Lang.DEFAULT_LANG, "????????????????"))
                .build());

        assertThat(instance.search(query)).isEqualTo(List.of(InlineQueryResultArticle.builder()
                .id(String.valueOf(city1.getId()))
                .title("????????????????")
                .inputMessageContent(InputTextMessageContent.builder()
                        .messageText("????????????????")
                        .build())
                .build()));
    }
}