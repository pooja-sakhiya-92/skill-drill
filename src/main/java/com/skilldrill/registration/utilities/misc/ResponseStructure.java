package com.skilldrill.registration.utilities.misc;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Data
@JsonIgnoreProperties
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseStructure<O> {

    private Integer code;
    private String msg;
    private O data;
    private Map<String,String> errors;
    private Date date;
    private String description;

    public static <O> ResponseStructure<O> createResponse(Integer code, String msg, O data) {
        ResponseStructure<O> rs = new ResponseStructure<>();
        rs.code = code;
        rs.msg = msg;
        rs.data = data;
        return rs;
    }

    public static <O> ResponseStructure<O> createResponse(Integer code, String msg) {
        ResponseStructure<O> rs = new ResponseStructure<>();
        rs.code = code;
        rs.msg = msg;
        return rs;
    }

    public static ResponseStructure<Void> createResponse(Integer code, String msg, Map<String,String > errors) {
        ResponseStructure<Void> rs = new ResponseStructure<>();
        rs.code = code;
        rs.msg = msg;
        rs.errors = errors;
        return rs;
    }
}
