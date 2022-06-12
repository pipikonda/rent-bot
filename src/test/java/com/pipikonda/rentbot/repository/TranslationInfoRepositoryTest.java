package com.pipikonda.rentbot.repository;

import com.pipikonda.rentbot.TestContainersBaseClass;
import com.pipikonda.rentbot.domain.TranslationInfo;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class TranslationInfoRepositoryTest extends TestContainersBaseClass {

    @Autowired
    private TranslationInfoRepository instance;

    @BeforeEach
    @AfterEach
    void clearDb() {
        instance.deleteAll();
    }

    @Test
    @Transactional
    void testSave() {
        TranslationInfo translationInfo = instance.save(TranslationInfo.builder()
                        .translationType(TranslationInfo.TranslationType.CITY_NAME)
                .build());

        assertThat(instance.findById(translationInfo.getId())).isPresent();
        assertThat(translationInfo.getId()).isNotNull();
    }

    @Test
    @Transactional
    void testGetAllByTranslationType_shouldReturnEmptyStream() {
        assertThat(instance.getAllByTranslationType(null)).isEmpty();
    }

    @Test
    @Transactional
    void testGetAllByTranslationType() {
        TranslationInfo translationInfo1 = instance.save(TranslationInfo.builder()
                .translationType(TranslationInfo.TranslationType.CITY_NAME)
                .build());
        TranslationInfo translationInfo2 = instance.save(TranslationInfo.builder()
                .translationType(TranslationInfo.TranslationType.CITY_NAME)
                .build());

        assertThat(instance.getAllByTranslationType(TranslationInfo.TranslationType.CITY_NAME))
                .isEqualTo(List.of(translationInfo1.getId(), translationInfo2.getId()));
    }
}
