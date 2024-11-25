package com.book.store.system.order;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@Import(TestcontainersConfiguration.class)
@SpringBootTest
class OrderServiceApplicationTests extends AbstractIntegrationTest {

    @Test
    void contextLoads() {}
}
