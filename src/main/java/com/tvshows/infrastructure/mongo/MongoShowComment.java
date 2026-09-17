package com.tvshows.infrastructure.mongo;

import java.math.BigDecimal;
import java.time.Instant;

import org.springframework.data.annotation.Id;

@org.springframework.data.mongodb.core.mapping.Document(collection = "show_comments")
public record MongoShowComment(@Id String id, long showId, String comment, BigDecimal rating, Instant createdAt) {
}
