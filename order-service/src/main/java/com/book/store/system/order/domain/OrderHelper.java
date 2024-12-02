package com.book.store.system.order.domain;

import com.book.store.system.order.client.catalog.CatalogServiceClient;
import com.book.store.system.order.client.catalog.Product;
import com.book.store.system.order.domain.exceptions.InvalidOrderException;
import com.book.store.system.order.domain.models.CreateOrderRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class OrderHelper {

    private final CatalogServiceClient catalogServiceClient;

    public void validateOrder(CreateOrderRequest createOrderRequest) {
        createOrderRequest.items().forEach(item -> {
            Product product = catalogServiceClient
                    .getProductByCode(item.code())
                    .orElseThrow(() -> new InvalidOrderException("Invalid order code: " + item.code()));
            if (item.price().compareTo(product.price()) != 0) {
                log.error(
                        "Product price not matching. Actual price: {}, received price: {}",
                        product.price(),
                        item.price());
                throw new InvalidOrderException("Product price not matching.");
            }
        });
    }
}
