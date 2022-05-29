package com.pipikonda.rentbot.repository;

import com.pipikonda.rentbot.TestContainersBaseClass;
import com.pipikonda.rentbot.domain.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class UserRepositoryTest extends TestContainersBaseClass {

    @Autowired
    private UserRepository instance;

    @BeforeEach
    @AfterEach
    void clearDb() {
        instance.deleteAll();
    }

    @Test
    void testSave() {
        User user = instance.save(User.builder()
                .id(123L)
                .name("name")
                .state(User.UserState.SUBSCRIBED)
                .username("some username")
                .lastName("surname")
                .build());

        assertThat(instance.findById(user.getId())).isPresent()
                .hasValueSatisfying(e -> assertThat(e).isEqualTo(user));

    }
}
