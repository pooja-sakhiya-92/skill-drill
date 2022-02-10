package com.skilldrill.registration.model.enums;

import org.springframework.security.core.GrantedAuthority;

public enum Roles implements GrantedAuthority {
    ROLE_ADMIN, ROLE_TOPIC_ADMIN, ROLE_GENERAL;

    @Override
    public String getAuthority() {
        return name();
    }
}
