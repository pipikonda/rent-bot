package com.pipikonda.rentbot.service;

import com.pipikonda.rentbot.domain.Lang;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
@RequiredArgsConstructor
@Slf4j
public class CityCacheEvictor {
    private final CacheManager cacheManager;

    public void clearMenuCache() {
        Arrays.stream(Lang.values()).forEach(e -> {
            try {
                cacheManager.getCache("CITY_NAME").clear();
            } catch (Exception ex) {
                log.warn("error while evicting cache CITY_NAME");
            }
        });
    }
}

