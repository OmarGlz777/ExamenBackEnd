package com.tvshows.application.port.in;

import com.tvshows.domain.Show;
import java.util.List;

public interface SearchShowsUseCase {
    List<Show> search(String query);
}
