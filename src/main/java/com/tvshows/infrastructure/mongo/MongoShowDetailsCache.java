package com.tvshows.infrastructure.mongo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tvshows.application.port.out.ShowDetailsCache;
import org.bson.Document;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class MongoShowDetailsCache implements ShowDetailsCache {
    private final MongoTemplate mongoTemplate;
    private final ObjectMapper objectMapper;

    public MongoShowDetailsCache(MongoTemplate mongoTemplate, ObjectMapper objectMapper) {
        this.mongoTemplate = mongoTemplate;
        this.objectMapper = objectMapper;
    }

    @Override
    public Optional<JsonNode> findById(long showId) {
        return Optional.ofNullable(mongoTemplate.findById(showId, CachedShow.class))
                .map(CachedShow::show)
                .map(this::toJsonNode);
    }

    @Override
    public void save(long showId, JsonNode show) {
        mongoTemplate.save(new CachedShow(showId, Document.parse(show.toString())));
    }

    private JsonNode toJsonNode(Document show) {
        try {
            return objectMapper.readTree(show.toJson());
        } catch (JsonProcessingException exception) {
            throw new IllegalStateException("Unable to read cached show", exception);
        }
    }
}
