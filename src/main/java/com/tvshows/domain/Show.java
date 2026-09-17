package com.tvshows.domain;

import java.util.List;

public record Show(long id, String name, String channel, String summary, List<String> genres) {}
