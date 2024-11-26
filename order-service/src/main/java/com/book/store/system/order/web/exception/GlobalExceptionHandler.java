package com.book.store.system.order.web.exception;

import com.book.store.system.order.domain.OrderNotFoundException;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.*;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private static final String SERVICE_KEY = "service";
    private static final String SERVICE_NAME = "order-service";
    private static final String TIMESTAMP_KEY = "timestamp";

    @ExceptionHandler(Exception.class)
    ProblemDetail handle(Exception exception) {
        exception.printStackTrace();
        ProblemDetail problemDetail =
                ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage());
        problemDetail.setTitle("Internal Server Error");
        problemDetail.setProperty(SERVICE_KEY, SERVICE_NAME);
        problemDetail.setProperty(TIMESTAMP_KEY, Instant.now());
        return problemDetail;
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException exception, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        exception.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String message = error.getDefaultMessage();
            errors.put(fieldName, message);
        });

        ProblemDetail problemDetail =
                ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, "Invalid request payload");
        problemDetail.setTitle("Validation Error");
        problemDetail.setProperty("errors", errors);
        problemDetail.setProperty(SERVICE_KEY, SERVICE_NAME);
        problemDetail.setProperty(TIMESTAMP_KEY, Instant.now());
        return new ResponseEntity<>(problemDetail, HttpStatus.BAD_REQUEST);
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
