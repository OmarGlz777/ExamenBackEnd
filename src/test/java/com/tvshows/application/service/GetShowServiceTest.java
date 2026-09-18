package com.tvshows.application.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tvshows.application.port.out.ShowCommentQuery;
import com.tvshows.domain.Comment;
import com.tvshows.application.port.out.ShowDetailsCache;
import com.tvshows.application.port.out.ShowDetailsProvider;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.mock;

class GetShowServiceTest {
    private final ShowDetailsProvider provider = mock(ShowDetailsProvider.class);
    private final ShowDetailsCache cache = mock(ShowDetailsCache.class);
    private final ShowCommentQuery showCommentQuery = mock(ShowCommentQuery.class);
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final GetShowService service = new GetShowService(provider, cache, showCommentQuery, objectMapper);

    @Test
    void returnsTheCachedShowWithoutCallingTvMaze() throws Exception {
        var cachedShow = objectMapper.readTree("{\"id\": 1, \"name\": \"Girls\"}");
        when(cache.findById(1)).thenReturn(Optional.of(cachedShow));

        when(showCommentQuery.findByShowIds(List.of(1L))).thenReturn(Map.of(
                1L, List.of(new Comment("Excellent", new BigDecimal("5.0")))));

        var result = service.getById(1);

        assertEquals("Girls", result.path("name").asText());
        assertEquals("Excellent", result.path("comments").get(0).path("comment").asText());
        assertEquals(false, cachedShow.has("comments"));

        verify(cache).findById(1);
        verify(provider, never()).findById(1);
        verify(cache, never()).save(1, cachedShow);
        verify(showCommentQuery).findByShowIds(List.of(1L));
    }

    @Test
    void retrievesAndCachesTheShowWhenItIsNotCached() throws Exception {
        var apiShow = objectMapper.readTree("{\"id\": 1, \"name\": \"Girls\"}");
        when(cache.findById(1)).thenReturn(Optional.empty());
        when(provider.findById(1)).thenReturn(apiShow);

        when(showCommentQuery.findByShowIds(List.of(1L))).thenReturn(Map.of());

        var result = service.getById(1);

        assertEquals(0, result.path("comments").size());
        assertEquals(false, apiShow.has("comments"));

        verify(provider).findById(1);
        verify(cache).save(1, apiShow);
        verify(showCommentQuery).findByShowIds(List.of(1L));
    }
}
