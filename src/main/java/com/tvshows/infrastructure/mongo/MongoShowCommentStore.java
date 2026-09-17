package com.tvshows.infrastructure.mongo;

import com.tvshows.application.port.out.ShowCommentStore;
import com.tvshows.domain.ShowComment;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class MongoShowCommentStore implements ShowCommentStore {
    private final MongoTemplate mongoTemplate;

    public MongoShowCommentStore(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public void save(ShowComment showComment) {
        mongoTemplate.save(new MongoShowComment(
                null,
                showComment.showId(),
                showComment.comment(),
                showComment.rating(),
                Instant.now()));
    }
}
