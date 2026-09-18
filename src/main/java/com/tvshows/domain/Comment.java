package com.tvshows.domain;

import java.math.BigDecimal;

public record Comment(String comment, BigDecimal rating) {
}
