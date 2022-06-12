package com.pipikonda.rentbot.service.inline;

import com.pipikonda.rentbot.error.BasicLogicException;
import com.pipikonda.rentbot.error.ErrorCode;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class SearchFactory {

    private final Map<SearchType, SearchService> searchMap;

    public SearchFactory(List<SearchService> searchMap) {
        this.searchMap = searchMap.stream().collect(Collectors.toMap(SearchService::supports, Function.identity()));
    }

    public SearchService getSearchService(SearchType type) {
        return Optional.ofNullable(type).map(searchMap::get)
                .orElseThrow(() -> new BasicLogicException(ErrorCode.NOT_FOUND, "Unexpected search service"));
    }
}
