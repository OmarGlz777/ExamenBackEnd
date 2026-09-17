package com.tvshows.api;

import com.fasterxml.jackson.databind.JsonNode;
import com.tvshows.application.port.in.GetShowUseCase;
import com.tvshows.application.port.in.SearchShowsUseCase;
import com.tvshows.domain.Show;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@Validated
@RestController
@RequestMapping("/api/shows")
public class ShowSearchController {
    private final SearchShowsUseCase searchShowsUseCase;
    private final GetShowUseCase getShowUseCase;

    public ShowSearchController(SearchShowsUseCase searchShowsUseCase, GetShowUseCase getShowUseCase) {
        this.searchShowsUseCase = searchShowsUseCase;
        this.getShowUseCase = getShowUseCase;
    }

    @GetMapping("/search")
    public List<Show> search(@RequestParam("search_query") @NotBlank String searchQuery) {
        return searchShowsUseCase.search(searchQuery);
    }

    @GetMapping("/{show_id}")
    public JsonNode getById(@PathVariable("show_id") @Positive long showId) {
        return getShowUseCase.getById(showId);
    }
}
