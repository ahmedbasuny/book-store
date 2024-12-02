package com.book.store.system.order.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("orders")
public record ApplicationProperties(String catalogServiceUrl) {}
