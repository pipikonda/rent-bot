package com.pipikonda.rentbot.service;

import com.pipikonda.rentbot.controller.CityController;
import com.pipikonda.rentbot.domain.City;
import com.pipikonda.rentbot.domain.Translation;
import com.pipikonda.rentbot.domain.TranslationInfo;
import com.pipikonda.rentbot.error.BasicLogicException;
import com.pipikonda.rentbot.error.ErrorCode;
import com.pipikonda.rentbot.repository.CityRepository;
import com.pipikonda.rentbot.repository.TranslationInfoRepository;
import com.pipikonda.rentbot.repository.TranslationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CityService {

    private final CityRepository cityRepository;
    private final TranslationService translationService;

    public City create(CityController.CityCreateDto dto) {
        return cityRepository.save(City.builder()
                .translationId(translationService.saveTranslations(dto.getNames(), TranslationInfo.TranslationType.CITY_NAME))
                .build());
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
    public List<CityController.CityDto> findByTranslationsId(Set<Long> translations) {
        Map<Long, List<Translation>> translationList = translationService.getTranslationsMap(translations);

        return cityRepository.findByTranslationIdList(translations)
                .map(e -> CityController.CityDto.builder()
                        .id(e.getId())
                        .names(translationList.get(e.getTranslationId())
                                .stream()
                                .collect(Collectors.toMap(Translation::getLang, Translation::getValue)))
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
}
