package com.skilldrill.registration.dto;

import lombok.Data;

@Data
public class UserDto {

    private String id;

    private String firstName;

    private String lastName;

    private String email;

    private Long phone;

    private String password;

    private String position;

    private String department;

    private String role;

    private Boolean active;

    private Integer otp;

    private Boolean updateFlag;
}
