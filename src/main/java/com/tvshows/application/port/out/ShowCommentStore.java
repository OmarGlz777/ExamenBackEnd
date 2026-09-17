package com.tvshows.application.port.out;

import com.tvshows.domain.ShowComment;

public interface ShowCommentStore {
    void save(ShowComment showComment);
}
