package com.book.store.system.order.web.controllers;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

import com.book.store.system.order.AbstractIntegrationTest;
import com.book.store.system.order.domain.models.CreateOrderRequest;
import com.book.store.system.order.domain.models.OrderSummary;
import com.book.store.system.order.testdata.TestDataFactory;
import io.restassured.common.mapper.TypeRef;
import io.restassured.http.ContentType;
import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.jdbc.Sql;

@Sql("/test-orders.sql")
class OrderControllerTest extends AbstractIntegrationTest {

    @Nested
    class CreateOrderTests {
        @Test
        void shouldCreateOrderSuccessfully() {
            mockGetProductByCode("P100", "book 1", new BigDecimal("25.50"));
            String payload =
                    """
                            {
                                "customer": {
                                    "name": "Ahmed Basuny",
                                    "email": "ahmedbasuny13@gmail.com",
                                    "phone": "01276063525"
                                },
                                "deliveryAddress": {
                                    "addressLine1": "Egypt",
                                    "addressLine2": "Cairo",
                                    "city": "Alex",
                                    "state": "Alex",
                                    "zipCode": "12345",
                                    "country": "Egypt"
                                },
                                "items": [
                                    {
                                        "code": "P100",
                                        "name": "book 1",
                                        "price": 25.50,
                                        "quantity": 1
                                    }
                                ]
                            }
                            """;
            given().contentType(ContentType.JSON)
                    .body(payload)
                    .when()
                    .post("/api/v1/orders")
                    .then()
                    .statusCode(HttpStatus.CREATED.value())
                    .body("orderNumber", notNullValue());
        }

        @Test
        void shouldReturnBadRequestWhenMandatoryDataIsMissing() {
            CreateOrderRequest payload = TestDataFactory.createOrderRequestWithInvalidCustomer();
            given().contentType(ContentType.JSON)
                    .body(payload)
                    .when()
                    .post("/api/v1/orders")
                    .then()
                    .statusCode(HttpStatus.BAD_REQUEST.value());
        }
    }

    @Nested
    class GetOrdersTests {
        @Test
        void shouldGetOrdersSuccessfully() {
            List<OrderSummary> orderSummaries = given().when()
                    .get("/api/v1/orders")
                    .then()
                    .statusCode(HttpStatus.OK.value())
                    .extract()
                    .body()
                    .as(new TypeRef<>() {});

            assertThat(orderSummaries).hasSize(2);
        }
    }

    @Nested
    class GetOrderByOrderNumberTests {
        String orderNumber = "order-123";

        @Test
        void shouldGetOrderSuccessfully() {
            given().when()
                    .get("/api/v1/orders/{orderNumber}", orderNumber)
                    .then()
                    .statusCode(HttpStatus.OK.value())
                    .body("orderNumber", is(orderNumber))
                    .body("items.size()", is(2));
        }
    }
}
