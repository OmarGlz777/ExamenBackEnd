package com.tvshows.application.service;

import com.tvshows.application.port.in.SearchShowsUseCase;
import com.tvshows.application.port.out.ShowSearchProvider;
import com.tvshows.domain.Show;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class SearchShowsService implements SearchShowsUseCase {
    private final ShowSearchProvider showSearchProvider;

    public SearchShowsService(ShowSearchProvider showSearchProvider) {
        this.showSearchProvider = showSearchProvider;
    }

    @Override
    public List<Show> search(String query) {
        return showSearchProvider.searchBy(query);
    }
}
