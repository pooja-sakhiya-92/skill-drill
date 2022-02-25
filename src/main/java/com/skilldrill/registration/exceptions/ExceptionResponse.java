package com.skilldrill.registration.exceptions;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

import java.util.Date;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ExceptionResponse {

    private Date timeStamp;
    private final String msg;
    private final Integer code;
    private String details;


    public ExceptionResponse(Date timeStamp, String msg, Integer code, String details) {
        this.timeStamp = timeStamp;
        this.msg = msg;
        this.code = code;
        this.details = details;
    }

    public ExceptionResponse(Integer code,String msg) {
        this.code = code;
        this.msg = msg;
    }

}
