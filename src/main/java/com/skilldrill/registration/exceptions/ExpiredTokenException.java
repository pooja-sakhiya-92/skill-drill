package com.skilldrill.registration.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class ExpiredTokenException extends RuntimeException {
    public ExpiredTokenException(String msg) {
        super(msg);
    }
}
