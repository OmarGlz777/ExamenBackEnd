package com.tvshows.application.service;

import com.tvshows.application.port.in.SearchShowsUseCase;
import com.tvshows.application.port.out.ShowCommentQuery;
import com.tvshows.application.port.out.ShowSearchProvider;
import com.tvshows.domain.Comment;
import com.tvshows.domain.Show;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;

@Service
public class SearchShowsService implements SearchShowsUseCase {
    private final ShowSearchProvider showSearchProvider;
    private final ShowCommentQuery showCommentQuery;

    public SearchShowsService(ShowSearchProvider showSearchProvider, ShowCommentQuery showCommentQuery) {
        this.showSearchProvider = showSearchProvider;
        this.showCommentQuery = showCommentQuery;
    }

    @Override
    public List<Show> search(String query) {
        List<Show> shows = showSearchProvider.searchBy(query);
        Map<Long, List<Comment>> commentsByShowId = showCommentQuery.findByShowIds(
                shows.stream().map(Show::id).toList());

        return shows.stream()
                .map(show -> show.withComments(commentsByShowId.getOrDefault(show.id(), List.of())))
                .toList();
    }
}
