package com.book.store.system.order.web.controllers;

import com.book.store.system.order.domain.OrderService;
import com.book.store.system.order.domain.SecurityService;
import com.book.store.system.order.domain.models.CreateOrderRequest;
import com.book.store.system.order.domain.models.CreateOrderResponse;
import com.book.store.system.order.domain.models.OrderDto;
import com.book.store.system.order.domain.models.OrderSummary;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping
    ResponseEntity<List<OrderSummary>> getOrders() {
        String userName = securityService.getLoggedInUserName();
        log.info("Fetching orders for user: {}", userName);
        return new ResponseEntity<>(orderService.getOrders(userName), HttpStatus.OK);
    }

    @GetMapping("/{orderNumber}")
    ResponseEntity<OrderDto> getOrder(@PathVariable String orderNumber) {
        String userName = securityService.getLoggedInUserName();
        log.info("Fetching order by id: {}, for user: {}", orderNumber, userName);
        return new ResponseEntity<>(orderService.getOrder(orderNumber, userName), HttpStatus.OK);
    }
}
