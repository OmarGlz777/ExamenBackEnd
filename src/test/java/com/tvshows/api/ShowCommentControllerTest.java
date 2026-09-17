package com.tvshows.api;

import com.tvshows.application.port.in.CreateShowCommentUseCase;
import com.tvshows.domain.ShowComment;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ShowCommentController.class)
class ShowCommentControllerTest {
    @Autowired private MockMvc mockMvc;
    @MockBean private CreateShowCommentUseCase createShowCommentUseCase;

    @Test
    void storesAValidCommentAndReturnsCreated() throws Exception {
        mockMvc.perform(post("/api/shows/1/comments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"comment\":\"Great show\",\"rating\":4.5}"))
                .andExpect(status().isCreated());

        verify(createShowCommentUseCase)
                .create(new ShowComment(1, "Great show", new BigDecimal("4.5")));
    }

    @Test
    void rejectsARatingOutsideTheAllowedRange() throws Exception {
        mockMvc.perform(post("/api/shows/1/comments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"comment\":\"Great show\",\"rating\":5.1}"))
                .andExpect(status().isBadRequest());
    }
}
