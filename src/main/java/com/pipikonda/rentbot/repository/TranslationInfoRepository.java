package com.pipikonda.rentbot.repository;

import com.pipikonda.rentbot.domain.TranslationInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Stream;

@Repository
public interface TranslationInfoRepository extends JpaRepository<TranslationInfo, Long> {

    @Query("select ti.id from TranslationInfo ti where ti.translationType = :translationType")
    List<Long> getAllByTranslationType(@Param("translationType") TranslationInfo.TranslationType translationType);

    Stream<TranslationInfo> findByTranslationType(TranslationInfo.TranslationType type);

}
