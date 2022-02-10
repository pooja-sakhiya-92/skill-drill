package com.skilldrill.registration.exceptions;

import java.util.Date;

public class ExceptionResponse {

    private final Date timeStamp;
    private final String msg;
    private final String status;
    private final String details;


    public ExceptionResponse(Date timeStamp, String msg, String status, String details) {
        this.timeStamp = timeStamp;
        this.msg = msg;
        this.status = status;
        this.details = details;
    }


    public Date getTimeStamp() {
        return timeStamp;
    }

    public String getMsg() {
        return msg;
    }

    public String getStatus() {
        return status;
    }

    public String getDetails() {
        return details;
    }
}
