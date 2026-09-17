package com.tvshows.application.service;

import com.tvshows.application.port.in.CreateShowCommentUseCase;
import com.tvshows.application.port.out.ShowCommentStore;
import com.tvshows.domain.ShowComment;
import org.springframework.stereotype.Service;

@Service
public class CreateShowCommentService implements CreateShowCommentUseCase {
    private final ShowCommentStore showCommentStore;

    public CreateShowCommentService(ShowCommentStore showCommentStore) {
        this.showCommentStore = showCommentStore;
    }

    @Override
    public void create(ShowComment showComment) {
        showCommentStore.save(showComment);
    }
}
