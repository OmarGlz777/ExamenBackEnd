package com.tvshows.application.port.out;

import com.tvshows.domain.Show;
import java.util.List;

/** Boundary for any source capable of searching TV shows. */
public interface ShowSearchProvider {
    List<Show> searchBy(String query);
}
