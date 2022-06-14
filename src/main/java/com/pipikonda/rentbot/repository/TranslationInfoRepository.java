package com.pipikonda.rentbot.repository;

import com.pipikonda.rentbot.domain.TranslationInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.stream.Stream;

@Repository
public interface TranslationInfoRepository extends JpaRepository<TranslationInfo, Long> {

    Stream<TranslationInfo> findByTranslationType(TranslationInfo.TranslationType type);
}
