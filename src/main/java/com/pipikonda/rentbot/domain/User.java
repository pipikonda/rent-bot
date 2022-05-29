package com.pipikonda.rentbot.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Entity
@Table(name = "users")
@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @Column(name = "id")
    Long id;

    @Column(name = "name")
    String name;

    @Column(name = "last_name")
    String lastName;

    @Column(name = "username")
    String username;

    @Enumerated(EnumType.STRING)
    @Column(name = "state")
    UserState state;

    public enum UserState {

        SUBSCRIBED,
        UNSUBSCRIBED,
        BLOCKED;

        private static final Map<String, UserState> map = Arrays.stream(values())
                .collect(Collectors.toMap(UserState::getJsonValue, Function.identity()));

        private String getJsonValue() {
            return name().toLowerCase();
        }

        public static UserState from(String state) {
            return map.get(state);
        }
    }
}
