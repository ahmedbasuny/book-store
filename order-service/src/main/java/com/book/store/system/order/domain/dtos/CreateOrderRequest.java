package com.book.store.system.order.domain.dtos;

import com.book.store.system.order.domain.models.Address;
import com.book.store.system.order.domain.models.Customer;
import com.book.store.system.order.domain.models.OrderItem;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import java.util.Set;

public record CreateOrderRequest(
        @Valid @NotEmpty(message = "Items cannot be empty") Set<OrderItem> items,
        @Valid Customer customer,
        @Valid Address deliveryAddress) {}
