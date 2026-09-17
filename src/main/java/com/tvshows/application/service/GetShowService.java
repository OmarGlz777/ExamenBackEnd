package com.tvshows.application.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.tvshows.application.port.in.GetShowUseCase;
import com.tvshows.application.port.out.ShowDetailsCache;
import com.tvshows.application.port.out.ShowDetailsProvider;
import org.springframework.stereotype.Service;

@Service
public class GetShowService implements GetShowUseCase {
    private final ShowDetailsProvider showDetailsProvider;
    private final ShowDetailsCache showDetailsCache;

    public GetShowService(ShowDetailsProvider showDetailsProvider, ShowDetailsCache showDetailsCache) {
        this.showDetailsProvider = showDetailsProvider;
        this.showDetailsCache = showDetailsCache;
    }

    @Override
    public JsonNode getById(long showId) {
        return showDetailsCache.findById(showId)
                .orElseGet(() -> getAndCache(showId));
    }

    private JsonNode getAndCache(long showId) {
        JsonNode show = showDetailsProvider.findById(showId);
        showDetailsCache.save(showId, show);
        return show;
    }
}
