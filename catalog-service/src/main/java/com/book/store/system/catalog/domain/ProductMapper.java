package com.book.store.system.catalog.domain;

import org.springframework.stereotype.Component;

@Component
class ProductMapper {

    public static Product productEntityToProduct(ProductEntity productEntity) {
        return new Product(
                productEntity.getCode(),
                productEntity.getName(),
                productEntity.getDescription(),
                productEntity.getImageUrl(),
                productEntity.getPrice()
        );
    }
}
