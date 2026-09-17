package com.tvshows.application.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.tvshows.application.port.in.GetShowUseCase;
import com.tvshows.application.port.out.ShowDetailsProvider;
import org.springframework.stereotype.Service;

@Service
public class GetShowService implements GetShowUseCase {
    private final ShowDetailsProvider showDetailsProvider;

    public GetShowService(ShowDetailsProvider showDetailsProvider) {
        this.showDetailsProvider = showDetailsProvider;
    }

    @Override
    public JsonNode getById(long showId) {
        return showDetailsProvider.findById(showId);
    }
}
