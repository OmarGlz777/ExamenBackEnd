package com.tvshows.infrastructure.mongo;

import org.bson.Document;
import org.springframework.data.annotation.Id;

@org.springframework.data.mongodb.core.mapping.Document(collection = "show_cache")
public record CachedShow(@Id long id, Document show) {
}
