package com.tvshows.application.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tvshows.application.port.out.ShowDetailsCache;
import com.tvshows.application.port.out.ShowDetailsProvider;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.mock;

class GetShowServiceTest {
    private final ShowDetailsProvider provider = mock(ShowDetailsProvider.class);
    private final ShowDetailsCache cache = mock(ShowDetailsCache.class);
    private final GetShowService service = new GetShowService(provider, cache);
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void returnsTheCachedShowWithoutCallingTvMaze() throws Exception {
        var cachedShow = objectMapper.readTree("{\"id\": 1, \"name\": \"Girls\"}");
        when(cache.findById(1)).thenReturn(Optional.of(cachedShow));

        assertSame(cachedShow, service.getById(1));

        verify(cache).findById(1);
        verify(provider, never()).findById(1);
        verify(cache, never()).save(1, cachedShow);
    }

    @Test
    void retrievesAndCachesTheShowWhenItIsNotCached() throws Exception {
        var apiShow = objectMapper.readTree("{\"id\": 1, \"name\": \"Girls\"}");
        when(cache.findById(1)).thenReturn(Optional.empty());
        when(provider.findById(1)).thenReturn(apiShow);

        assertSame(apiShow, service.getById(1));

        verify(provider).findById(1);
        verify(cache).save(1, apiShow);
    }
}
