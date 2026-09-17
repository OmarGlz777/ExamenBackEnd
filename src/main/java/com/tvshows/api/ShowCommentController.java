package com.tvshows.api;

import com.tvshows.application.port.in.CreateShowCommentUseCase;
import com.tvshows.domain.ShowComment;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping("/api/shows")
public class ShowCommentController {
    private final CreateShowCommentUseCase createShowCommentUseCase;

    public ShowCommentController(CreateShowCommentUseCase createShowCommentUseCase) {
        this.createShowCommentUseCase = createShowCommentUseCase;
    }

    @PostMapping("/{show_id}/comments")
    public ResponseEntity<Void> create(
            @PathVariable("show_id") @Positive long showId,
            @Valid @RequestBody CreateShowCommentRequest request) {
        createShowCommentUseCase.create(new ShowComment(showId, request.comment(), request.rating()));
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
