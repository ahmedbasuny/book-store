package com.book.store.system.catalog.web.exception;

import com.book.store.system.catalog.domain.ProductNotFoundException;
import java.time.Instant;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private static final String SERVICE_KEY = "service";
    private static final String SERVICE_NAME = "catalog-service";
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

    @ExceptionHandler(ProductNotFoundException.class)
    ProblemDetail handle(ProductNotFoundException exception) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, exception.getMessage());
        problemDetail.setTitle("Product Not Found");
        problemDetail.setProperty(SERVICE_KEY, SERVICE_NAME);
        problemDetail.setProperty(TIMESTAMP_KEY, Instant.now());
        return problemDetail;
    }
}
