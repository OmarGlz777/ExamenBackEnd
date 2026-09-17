package com.tvshows.infrastructure.tvmaze;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tvshows.application.port.out.ShowSearchProvider;
import com.tvshows.domain.Show;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import java.util.List;

@Component
public class TvMazeShowSearchClient implements ShowSearchProvider {
    private final RestClient restClient;

    public TvMazeShowSearchClient(RestClient.Builder restClientBuilder) {
        this.restClient = restClientBuilder.baseUrl("https://api.tvmaze.com").build();
    }

    @Override
    public List<Show> searchBy(String query) {
        TvMazeSearchResult[] results = restClient.get()
                .uri(builder -> builder.path("/search/shows").queryParam("q", query).build())
                .retrieve()
                .body(TvMazeSearchResult[].class);

        if (results == null) return List.of();
        return List.of(results).stream().map(this::toShow).toList();
    }

    private Show toShow(TvMazeSearchResult result) {
        TvMazeShow show = result.show();
        String channel = show.network() != null ? show.network().name()
                : show.webChannel() == null ? null : show.webChannel().name();
        return new Show(show.id(), show.name(), channel, show.summary(),
                show.genres() == null ? List.of() : show.genres());
    }

    private record TvMazeSearchResult(TvMazeShow show) {}

    private record TvMazeShow(long id, String name, TvMazeChannel network,
                              @JsonProperty("webChannel") TvMazeChannel webChannel,
                              String summary, List<String> genres) {}

    private record TvMazeChannel(String name) {}
}
