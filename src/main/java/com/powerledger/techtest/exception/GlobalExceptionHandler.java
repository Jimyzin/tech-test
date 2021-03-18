package com.powerledger.techtest.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.validation.ConstraintViolationException;
import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ExceptionResponse> constraintViolationException(ConstraintViolationException ex) {
        ExceptionResponse response = new ExceptionResponse();
        response.setErrorCode("CONSTRAINT_VIOLATION");
        response.setErrorMessage(ex.getMessage());
        response.setTimestamp(LocalDateTime.now());

        return new ResponseEntity<ExceptionResponse>(response, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(TechTestException.class)
    public ResponseEntity<ExceptionResponse> techTestException(TechTestException ex) {
        ExceptionResponse response = new ExceptionResponse();
        response.setErrorCode("INVALID_INPUT");
        response.setErrorMessage(ex.getMessage());
        response.setTimestamp(LocalDateTime.now());

        return new ResponseEntity<ExceptionResponse>(response, HttpStatus.CONFLICT);
    }

}