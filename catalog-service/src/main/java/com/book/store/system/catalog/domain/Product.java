package com.book.store.system.catalog.domain;

import java.math.BigDecimal;

public record Product(
        String code,
        String name,
        String description,
        String imageUrl,
        BigDecimal price
) {
}
