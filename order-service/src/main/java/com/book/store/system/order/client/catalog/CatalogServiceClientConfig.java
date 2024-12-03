package com.book.store.system.order.client.catalog;

import com.book.store.system.order.configuration.ApplicationProperties;
import java.time.Duration;
import org.springframework.boot.http.client.ClientHttpRequestFactoryBuilder;
import org.springframework.boot.http.client.ClientHttpRequestFactorySettings;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.web.client.RestClient;

@Configuration
class CatalogServiceClientConfig {

    private static final Duration TIME_OUT_VALUE = Duration.ofSeconds(5);

    @Bean
    RestClient restClient(ApplicationProperties applicationProperties) {
        ClientHttpRequestFactorySettings settings = ClientHttpRequestFactorySettings.defaults()
                .withReadTimeout(TIME_OUT_VALUE)
                .withConnectTimeout(TIME_OUT_VALUE);
        ClientHttpRequestFactory requestFactory =
                ClientHttpRequestFactoryBuilder.detect().build(settings);
        return RestClient.builder()
                .baseUrl(applicationProperties.catalogServiceUrl())
                .requestFactory(requestFactory)
                .build();
    }
}
