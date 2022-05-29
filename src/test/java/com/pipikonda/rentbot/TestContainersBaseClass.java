package com.pipikonda.rentbot;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Slf4j
public class TestContainersBaseClass {

    @Container
    public static PostgreSQLContainer POSTGRES = new PostgreSQLContainer(DockerImageName.parse("postgres"))
            .withDatabaseName("test_database")
            .withUsername("foo")
            .withPassword("secret");

    @DynamicPropertySource
    static void properties(DynamicPropertyRegistry registry) {
        System.out.println(POSTGRES.getJdbcUrl());
        registry.add("spring.datasource.url", POSTGRES::getJdbcUrl);
        registry.add("spring.datasource.username", POSTGRES::getUsername);
        registry.add("spring.datasource.password", POSTGRES::getPassword);
    }
}
