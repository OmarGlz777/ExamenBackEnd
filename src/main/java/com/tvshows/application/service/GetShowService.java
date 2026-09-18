package com.tvshows.application.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tvshows.application.port.in.GetShowUseCase;
import com.tvshows.application.port.out.ShowCommentQuery;
import com.tvshows.application.port.out.ShowDetailsCache;
import com.tvshows.application.port.out.ShowDetailsProvider;
import com.tvshows.domain.Comment;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class GetShowService implements GetShowUseCase {
    private final ShowDetailsProvider showDetailsProvider;
    private final ShowDetailsCache showDetailsCache;
    private final ShowCommentQuery showCommentQuery;
    private final ObjectMapper objectMapper;

    public GetShowService(
            ShowDetailsProvider showDetailsProvider,
            ShowDetailsCache showDetailsCache,
            ShowCommentQuery showCommentQuery,
            ObjectMapper objectMapper) {
        this.showDetailsProvider = showDetailsProvider;
        this.showDetailsCache = showDetailsCache;
        this.showCommentQuery = showCommentQuery;
        this.objectMapper = objectMapper;
    }

    @Override
    public JsonNode getById(long showId) {
        JsonNode show = showDetailsCache.findById(showId)
                .orElseGet(() -> getAndCache(showId));
        return addComments(showId, show);
    }

    private JsonNode getAndCache(long showId) {
        JsonNode show = showDetailsProvider.findById(showId);
        showDetailsCache.save(showId, show);
        return show;
    }

    private JsonNode addComments(long showId, JsonNode show) {
        Map<Long, List<Comment>> commentsByShowId = showCommentQuery.findByShowIds(List.of(showId));
        JsonNode response = show.deepCopy();
        if (response.isObject()) {
            ((com.fasterxml.jackson.databind.node.ObjectNode) response).set(
                    "comments",
                    objectMapper.valueToTree(commentsByShowId.getOrDefault(showId, List.of())));
        }
        return response;
    }
}
