package com.pipikonda.rentbot.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
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

    @Enumerated(EnumType.STRING)
    @Column(name = "lang")
    Lang lang;

    public enum UserState {

        SUBSCRIBED,
        UNSUBSCRIBED,
        BLOCKED;
    }
}
