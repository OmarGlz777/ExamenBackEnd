package com.tvshows.application.port.out;

import com.fasterxml.jackson.databind.JsonNode;

public interface ShowDetailsProvider {
    JsonNode findById(long showId);
}
