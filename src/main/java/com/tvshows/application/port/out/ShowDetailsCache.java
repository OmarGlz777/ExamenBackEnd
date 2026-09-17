package com.tvshows.application.port.out;

import com.fasterxml.jackson.databind.JsonNode;

import java.util.Optional;

public interface ShowDetailsCache {
    Optional<JsonNode> findById(long showId);

    void save(long showId, JsonNode show);
}
