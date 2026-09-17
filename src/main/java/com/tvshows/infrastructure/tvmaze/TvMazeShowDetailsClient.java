package com.tvshows.infrastructure.tvmaze;

import com.fasterxml.jackson.databind.JsonNode;
import com.tvshows.application.port.out.ShowDetailsProvider;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.server.ResponseStatusException;

@Component
public class TvMazeShowDetailsClient implements ShowDetailsProvider {
    private final RestClient restClient;

    public TvMazeShowDetailsClient(RestClient.Builder restClientBuilder) {
        this.restClient = restClientBuilder.baseUrl("https://api.tvmaze.com").build();
    }

    @Override
    public JsonNode findById(long showId) {
        return restClient.get()
                .uri("/shows/{showId}", showId)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, (request, response) -> {
                    if (response.getStatusCode().value() == HttpStatus.NOT_FOUND.value()) {
                        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Show not found: " + showId);
                    }
                    throw new ResponseStatusException(response.getStatusCode(), "TVMaze rejected the request");
                })
                .body(JsonNode.class);
    }
}
