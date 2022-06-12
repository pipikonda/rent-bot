package com.pipikonda.rentbot.repository;

import com.pipikonda.rentbot.TestContainersBaseClass;
import com.pipikonda.rentbot.domain.Lang;
import com.pipikonda.rentbot.domain.Translation;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class TranslationRepositoryTest extends TestContainersBaseClass {

    @Autowired
    private TranslationRepository instance;

    @BeforeEach
    @AfterEach
    void clearDb() {
        instance.deleteAll();
    }

    @Test
    void testSave() {
        Translation translation = instance.save(Translation.builder()
                .value("message")
                .lang(Lang.RU)
                .translationId(12L)
                .build());

        assertThat(translation.getId()).isNotNull();
        assertThat(instance.findById(translation.getId())).isPresent();
    }

    @Test
    @Transactional
    void findByTranslationId_shouldReturnEmptyStream_whenTranslationIsNotFound() {
        assertThat(instance.findByTranslationId(56L)).isEmpty();
    }

    @Test
    @Transactional
    void testFindByTranslationId() {
        Translation translation1 = instance.save(Translation.builder()
                .value("сообщение")
                .lang(Lang.RU)
                .translationId(12L)
                .build());
        Translation translation2 = instance.save(Translation.builder()
                .value("повідомлення")
                .lang(Lang.UK)
                .translationId(12L)
                .build());
        Translation translation3 = instance.save(Translation.builder()
                .value("повідомлення")
                .lang(Lang.UK)
                .translationId(15L)
                .build());

        assertThat(instance.findByTranslationId(12L).toList())
                .isEqualTo(List.of(translation1, translation2));
    }

    @Test
    @Transactional
    void testFindAllByTranslationIdInAndValueLike() {
        Translation translation1 = instance.save(Translation.builder()
                        .lang(Lang.UK)
                        .translationId(1L)
                        .value("DNIPRO")
                .build());
        Translation translation2 = instance.save(Translation.builder()
                .lang(Lang.RU)
                .translationId(1L)
                .value("DNEPR")
                .build());
        Translation translation3 = instance.save(Translation.builder()
                .lang(Lang.RU)
                .translationId(1L)
                .value("RIVER")
                .build());
        Translation translation4 = instance.save(Translation.builder()
                .lang(Lang.RU)
                .translationId(2L)
                .value("DNIPRO RIVER")
                .build());

        assertThat(instance.findTranslationStartWithValue(List.of(1L), "DN").toList())
                .isEqualTo(List.of(translation1, translation2));
        assertThat(instance.findTranslationStartWithValue(List.of(4L), "DN").toList())
                .isEqualTo(List.of());
        assertThat(instance.findTranslationStartWithValue(List.of(1L), "IV").toList())
                .isEqualTo(List.of());
    }
}
