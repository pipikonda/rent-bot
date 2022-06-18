package com.pipikonda.rentbot.controller;

import com.pipikonda.rentbot.controller.validator.DefaultLang;
import com.pipikonda.rentbot.domain.City;
import com.pipikonda.rentbot.domain.Lang;
import com.pipikonda.rentbot.service.CityService;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class CityController {

    private final CityService cityService;

    @Value
    @Builder
    @Jacksonized
    public static class CityCreateDto {

        @NotEmpty
        @DefaultLang
        Map<Lang, String> names;
    }

    @PostMapping("/api/city")
    public City create(@Valid @RequestBody CityCreateDto dto) {
        return cityService.create(dto);
    }

    @Value
    @Builder
    @Jacksonized
    public static class CityDto implements Serializable {

        static final long serialVersionUID = 42L;
        Long id;
        Map<Lang, String> names;
    }

    @GetMapping("/api/city")
    public List<CityDto> getList() {
        return cityService.getList();
    }

    @GetMapping("/api/city/{id}")
    public CityDto getById(@Valid @NotNull @PathVariable("id") Long id) {
        return cityService.findById(id);
    }

    @Builder
    @Value
    public static class CityPatchDto {

        @NotEmpty
        Map<Lang, String> names;
    }
//
//    @PatchMapping("/api/city/{id}")
}
