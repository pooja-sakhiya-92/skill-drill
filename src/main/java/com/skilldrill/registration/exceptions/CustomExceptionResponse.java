package com.skilldrill.registration.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@RestControllerAdvice
public class CustomExceptionResponse extends ResponseEntityExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public final ResponseEntity<Object> handelNotFound(NotFoundException ex, WebRequest request) {
        ExceptionResponse exceptionResponse =
                new ExceptionResponse(
                        new Date(),
                        ex.getMessage(),
                        404,
                        request.getDescription(false));
        return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(wrongCredentialException.class)
    public final ResponseEntity<Object> handelWrongCredential(wrongCredentialException ex, WebRequest request) {
        ExceptionResponse exceptionResponse =
                new ExceptionResponse(
                        new Date(),
                        ex.getMessage(),
                        400,
                        request.getDescription(false));
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidFieldOrEmptyFieldException.class)
    public final ResponseEntity<Object> handleInvalidFieldOrEmptyField(InvalidFieldOrEmptyFieldException ex, WebRequest request) {
        ExceptionResponse exceptionResponse =
                new ExceptionResponse(
                        new Date(),
                        ex.getMessage(),
                        406,
                        request.getDescription(false));
        return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_ACCEPTABLE);
    }

    @ExceptionHandler(InvalidRequestException.class)
    public final ResponseEntity<Object> handleInvalidRequestException(InvalidRequestException ex, WebRequest request) {
        ExceptionResponse exceptionResponse =
                new ExceptionResponse(
                        new Date(),
                        ex.getMessage(),
                        417,
                        request.getDescription(false));
        return new ResponseEntity<>(exceptionResponse, HttpStatus.EXPECTATION_FAILED);
    }

    @ExceptionHandler(ExpiredTokenException.class)
    public final ResponseEntity<Object> handleExpiredTokenException(ExpiredTokenException ex, WebRequest request) {
        ExceptionResponse exceptionResponse =
                new ExceptionResponse(500,
                        ex.getMessage());
        return new ResponseEntity<>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
