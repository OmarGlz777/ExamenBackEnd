package com.tvshows.application.service;

import com.tvshows.application.port.out.ShowCommentQuery;
import com.tvshows.application.port.out.ShowSearchProvider;
import com.tvshows.domain.Comment;
import com.tvshows.domain.Show;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class SearchShowsServiceTest {
    private final ShowSearchProvider showSearchProvider = mock(ShowSearchProvider.class);
    private final ShowCommentQuery showCommentQuery = mock(ShowCommentQuery.class);
    private final SearchShowsService service = new SearchShowsService(showSearchProvider, showCommentQuery);

    @Test
    void attachesStoredCommentsToEachMatchingShow() {
        Show girls = new Show(1, "Girls", "HBO", "A comedy", List.of("Drama"));
        Show other = new Show(2, "Other", "Web", "Another show", List.of());
        when(showSearchProvider.searchBy("girls")).thenReturn(List.of(girls, other));
        when(showCommentQuery.findByShowIds(List.of(1L, 2L))).thenReturn(Map.of(
                1L, List.of(new Comment("Great show", new BigDecimal("4.5")))));

        List<Show> result = service.search("girls");

        assertEquals("Great show", result.get(0).comments().get(0).comment());
        assertEquals(new BigDecimal("4.5"), result.get(0).comments().get(0).rating());
        assertEquals(List.of(), result.get(1).comments());
        verify(showCommentQuery).findByShowIds(List.of(1L, 2L));
    }
}
