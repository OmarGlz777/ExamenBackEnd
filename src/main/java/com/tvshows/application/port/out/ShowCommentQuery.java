package com.tvshows.application.port.out;

import com.tvshows.domain.Comment;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface ShowCommentQuery {
    Map<Long, List<Comment>> findByShowIds(Collection<Long> showIds);
}
