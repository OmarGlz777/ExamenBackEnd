package com.tvshows.api;

import com.tvshows.application.port.in.SearchShowsUseCase;
import com.tvshows.domain.Show;
import jakarta.validation.constraints.NotBlank;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@Validated
@RestController
@RequestMapping("/api/shows")
public class ShowSearchController {
    private final SearchShowsUseCase searchShowsUseCase;

    public ShowSearchController(SearchShowsUseCase searchShowsUseCase) {
        this.searchShowsUseCase = searchShowsUseCase;
    }

    @GetMapping("/search")
    public List<Show> search(@RequestParam("search_query") @NotBlank String searchQuery) {
        return searchShowsUseCase.search(searchQuery);
    }
}
