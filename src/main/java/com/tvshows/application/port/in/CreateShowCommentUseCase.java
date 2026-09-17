package com.tvshows.application.port.in;

import com.tvshows.domain.ShowComment;

public interface CreateShowCommentUseCase {
    void create(ShowComment showComment);
}
