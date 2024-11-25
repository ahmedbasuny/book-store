package com.book.store.system.order.web.exception;

import com.book.store.system.order.domain.OrderNotFoundException;
import java.time.Instant;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private static final String SERVICE_KEY = "service";
    private static final String SERVICE_NAME = "order-service";
    private static final String TIMESTAMP_KEY = "timestamp";

    @ExceptionHandler(Exception.class)
    ProblemDetail handle(Exception exception) {
        ProblemDetail problemDetail =
                ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage());
        problemDetail.setTitle("Internal Server Error");
        problemDetail.setProperty(SERVICE_KEY, SERVICE_NAME);
        problemDetail.setProperty(TIMESTAMP_KEY, Instant.now());
        return problemDetail;
    }

    @ExceptionHandler(OrderNotFoundException.class)
    ProblemDetail handle(OrderNotFoundException exception) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, exception.getMessage());
        problemDetail.setTitle("Order Not Found");
        problemDetail.setProperty(SERVICE_KEY, SERVICE_NAME);
        problemDetail.setProperty(TIMESTAMP_KEY, Instant.now());
        return problemDetail;
    }
}
