package com.book.store.system.notification.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("notifications")
public record ApplicationProperties(String supportEmail) {}
