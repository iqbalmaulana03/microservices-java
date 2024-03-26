package com.iqbal.orderservice.exception;

import com.iqbal.orderservice.model.response.WebResponse;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ErrorController {

    @ExceptionHandler({ConstraintViolationException.class})
    public ResponseEntity<WebResponse<String>> handleConstraintViolationException(ConstraintViolationException e){

        WebResponse<String> response = WebResponse.<String>builder()
                .status(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .message(e.getMessage())
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
}
