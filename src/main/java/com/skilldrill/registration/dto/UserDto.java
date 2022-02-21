package com.skilldrill.registration.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.lang.Nullable;

@Data
@AllArgsConstructor
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

    @Nullable
    private TechnicalDetailsDto technicalDetails;

    @Nullable
    private Boolean active;
    @Nullable
    private Integer otp;
    @Nullable
    private Boolean updateFlag;

    public UserDto() {

    }
}
