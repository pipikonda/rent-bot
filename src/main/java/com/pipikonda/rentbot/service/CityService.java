package com.pipikonda.rentbot.service;

import com.pipikonda.rentbot.controller.CityController;
import com.pipikonda.rentbot.domain.City;
import com.pipikonda.rentbot.domain.Translation;
import com.pipikonda.rentbot.domain.TranslationInfo;
import com.pipikonda.rentbot.error.BasicLogicException;
import com.pipikonda.rentbot.error.ErrorCode;
import com.pipikonda.rentbot.repository.CityRepository;
import com.pipikonda.rentbot.repository.TranslationInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CityService {

    private final CityRepository cityRepository;
    private final TranslationService translationService;
    private final TranslationInfoRepository translationInfoRepository;
    private final CityCacheEvictor cityCacheEvictor;

    public City create(CityController.CityCreateDto dto) {
        City city = cityRepository.save(City.builder()
                .translationId(translationService.saveTranslations(dto.getNames(), TranslationInfo.TranslationType.CITY_NAME))
                .build());
        cityCacheEvictor.clearMenuCache();
        return city;
    }

    @Transactional
    public List<CityController.CityDto> getList() {
        return cityRepository.findAll()
                .stream()
                .map(e -> CityController.CityDto.builder()
                        .id(e.getId())
                        .names(translationService.getTranslationsAsMap(e.getTranslationId()))
                        .build())
                .toList();
    }

    @Transactional
    public CityController.CityDto findById(Long id) {
        return cityRepository.findById(id)
                .map(e -> CityController.CityDto.builder()
                        .id(e.getId())
                        .names(translationService.getTranslationsAsMap(e.getTranslationId()))
                        .build())
                .orElseThrow(() -> new BasicLogicException(ErrorCode.NOT_FOUND, "Not found city with id " + id));
    }

    @Transactional
    @Cacheable(value = "CITY_NAME", key = "#query")
    public List<CityController.CityDto> getCitiesByValue(String query) {
        Map<Long, List<TranslationInfo>> translationsId = translationInfoRepository.findByTranslationType(TranslationInfo.TranslationType.CITY_NAME)
                .collect(Collectors.groupingBy(TranslationInfo::getId));

        Map<Long, List<Translation>> translations = translationService.findByValueLike(query, translationsId.keySet())
                .stream()
                .collect(Collectors.groupingBy(Translation::getTranslationId));

        Map<Long, List<Translation>> allTranslations = translationService.getTranslationsMap(translationsId.keySet());

        return cityRepository.findByTranslationIdList(new HashSet<>(translations.keySet()))
                .map(e -> CityController.CityDto.builder()
                        .id(e.getId())
                        .names(allTranslations.get(e.getTranslationId())
                                .stream()
                                .collect(Collectors.toMap(Translation::getLang, Translation::getValue)))
                        .build())
                .toList();
    }
}
