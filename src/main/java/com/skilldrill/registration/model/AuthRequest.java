package com.skilldrill.registration.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AuthRequest {

    private String userName;

    private String password;

    public AuthRequest() {

    }

}
