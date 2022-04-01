package com.skilldrill.registration.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class wrongCredentialException extends RuntimeException {
    public wrongCredentialException(String msg) {
        super(msg);
    }
}
