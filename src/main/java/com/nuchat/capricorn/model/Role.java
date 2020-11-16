package com.nuchat.capricorn.model;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    ROLE_MEMBER;
    public String getAuthority() {
        return name();
    }

}