package com.tvshows.domain;

import java.math.BigDecimal;

public record ShowComment(long showId, String comment, BigDecimal rating) {
}
