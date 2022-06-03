package com.pipikonda.rentbot.service.http;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.Serializable;
import java.util.Optional;

@Service
public class SearchLocationClient {

    private final RestTemplate restTemplate;
    private final String url;


    public SearchLocationClient(RestTemplate restTemplate,
                                @Value("${service.open-street-map.get-city-url}") String url) {
        this.restTemplate = restTemplate;
        this.url = url;
    }

    @Cacheable(value = "CITY_INFO", key = "#cityName")
    public GetCityResponse[] getCity(String cityName) {
        return Optional.ofNullable(restTemplate.getForObject(url, GetCityResponse[].class, cityName))
                .orElse(new GetCityResponse[]{});
    }

    @lombok.Value
    @Builder
    @Jacksonized
    public static class GetCityResponse implements Serializable {

        Float lat;
        Float lon;

        @JsonProperty("display_name")
        String displayName;

        String type; //convert to enum ??

        @JsonProperty("class")
        String classification;
    }
}
