package com.book.store.system.order.web.controllers;

import com.book.store.system.order.domain.OrderService;
import com.book.store.system.order.domain.SecurityService;
import com.book.store.system.order.domain.models.CreateOrderRequest;
import com.book.store.system.order.domain.models.CreateOrderResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@Slf4j
@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {

    private final OrderService orderService;
    private final SecurityService securityService;

    @PostMapping
    ResponseEntity<CreateOrderResponse> createOrder(@Valid @RequestBody CreateOrderRequest createOrderRequest) {
        String userName = securityService.getLoggedInUserName();
        log.info("Creating order for user: {}", userName);
        return new ResponseEntity<>(orderService.createOrder(userName, createOrderRequest), HttpStatus.CREATED);
    }
}
