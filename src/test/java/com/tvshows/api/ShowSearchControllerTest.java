package com.tvshows.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tvshows.application.port.in.GetShowUseCase;
import com.tvshows.application.port.in.SearchShowsUseCase;
import com.tvshows.domain.Comment;
import com.tvshows.domain.Show;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import java.util.List;
import java.math.BigDecimal;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ShowSearchController.class)
class ShowSearchControllerTest {
    @Autowired private MockMvc mockMvc;
    @MockBean private SearchShowsUseCase searchShowsUseCase;
    @MockBean private GetShowUseCase getShowUseCase;

    @Test
    void returnsOnlyTheRequiredShowFields() throws Exception {
        when(searchShowsUseCase.search("girls"))
                .thenReturn(List.of(new Show(1, "Girls", "HBO", "A comedy", List.of("Drama"),
                        List.of(new Comment("Great show", new BigDecimal("4.5"))))));

        mockMvc.perform(get("/api/shows/search").param("search_query", "girls"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("Girls"))
                .andExpect(jsonPath("$[0].channel").value("HBO"))
                .andExpect(jsonPath("$[0].summary").value("A comedy"))
                .andExpect(jsonPath("$[0].genres[0]").value("Drama"))
                .andExpect(jsonPath("$[0].comments[0].comment").value("Great show"))
                .andExpect(jsonPath("$[0].comments[0].rating").value(4.5));

        verify(searchShowsUseCase).search("girls");
    }

    @Test
    void returnsTheCompleteShowObject() throws Exception {
        var show = new ObjectMapper().readTree("""
                {"id": 1, "name": "Girls", "rating": {"average": 7.8}, "_links": {"self": {"href": "https://api.tvmaze.com/shows/1"}}}
                """);
        when(getShowUseCase.getById(1)).thenReturn(show);

        mockMvc.perform(get("/api/shows/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.rating.average").value(7.8))
                .andExpect(jsonPath("$._links.self.href").value("https://api.tvmaze.com/shows/1"));

        verify(getShowUseCase).getById(1);
    }
}
