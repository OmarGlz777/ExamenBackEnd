package com.tvshows.application.port.in;

import com.fasterxml.jackson.databind.JsonNode;

public interface GetShowUseCase {
    JsonNode getById(long showId);
}
