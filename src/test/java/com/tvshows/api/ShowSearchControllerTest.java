package com.tvshows.api;

import com.tvshows.application.port.in.SearchShowsUseCase;
import com.tvshows.domain.Show;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import java.util.List;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ShowSearchController.class)
class ShowSearchControllerTest {
    @Autowired private MockMvc mockMvc;
    @MockBean private SearchShowsUseCase searchShowsUseCase;

    @Test
    void returnsOnlyTheRequiredShowFields() throws Exception {
        when(searchShowsUseCase.search("girls"))
                .thenReturn(List.of(new Show(1, "Girls", "HBO", "A comedy", List.of("Drama"))));

        mockMvc.perform(get("/api/shows/search").param("search_query", "girls"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("Girls"))
                .andExpect(jsonPath("$[0].channel").value("HBO"))
                .andExpect(jsonPath("$[0].summary").value("A comedy"))
                .andExpect(jsonPath("$[0].genres[0]").value("Drama"));

        verify(searchShowsUseCase).search("girls");
    }
}
