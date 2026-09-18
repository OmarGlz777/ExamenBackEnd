package com.tvshows.domain;

import java.util.List;

public record Show(long id, String name, String channel, String summary, List<String> genres, List<Comment> comments) {
    public Show(long id, String name, String channel, String summary, List<String> genres) {
        this(id, name, channel, summary, genres, List.of());
    }

    public Show withComments(List<Comment> comments) {
        return new Show(id, name, channel, summary, genres, comments);
    }
}
