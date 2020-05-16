package com.example.bookmyshow.entities;

import org.springframework.security.core.GrantedAuthority;

/**
 * Created by AbhijithRavuri.
 */
public enum Role implements GrantedAuthority {
    ROLE_ADMIN,
    ROLE_USER;

    private Role() {
    }

    public String getAuthority() {
        return this.name();
    }
}
