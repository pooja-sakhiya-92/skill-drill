package com.skilldrill.registration.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
public class InvalidFieldOrEmptyFieldException extends RuntimeException {
    public InvalidFieldOrEmptyFieldException(String msg) {
        super(msg);
    }
}
