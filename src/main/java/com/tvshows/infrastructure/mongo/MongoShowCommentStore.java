package com.tvshows.infrastructure.mongo;

import com.tvshows.application.port.out.ShowCommentQuery;
import com.tvshows.application.port.out.ShowCommentStore;
import com.tvshows.domain.Comment;
import com.tvshows.domain.ShowComment;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.mapping;
import static java.util.stream.Collectors.toList;

@Component
public class MongoShowCommentStore implements ShowCommentStore, ShowCommentQuery {
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

    @Override
    public Map<Long, List<Comment>> findByShowIds(Collection<Long> showIds) {
        if (showIds.isEmpty()) {
            return Map.of();
        }

        return mongoTemplate.find(
                        Query.query(Criteria.where("showId").in(showIds)),
                        MongoShowComment.class)
                .stream()
                .collect(groupingBy(
                        MongoShowComment::showId,
                        mapping(comment -> new Comment(comment.comment(), comment.rating()), toList())));
    }
}
