package com.book.store.system.order.domain.models;

import com.book.store.system.order.domain.enums.OrderStatus;

public record OrderSummary(String orderNumber, OrderStatus orderStatus) {}
