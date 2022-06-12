package com.pipikonda.rentbot.domain;

import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum Lang {

    RU,
    UK;

    public static final Lang DEFAULT_LANG = UK;
    private static final Map<String, Lang> map = Stream.of(values())
            .collect(Collectors.toMap(Lang::getJsonValue, Function.identity()));

    @JsonValue
    public String getJsonValue() {
        return name().toLowerCase();
    }

    public static Lang from(String lang) {
        return map.getOrDefault(lang, DEFAULT_LANG);
    }
}
