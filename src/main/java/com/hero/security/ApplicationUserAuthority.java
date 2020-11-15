package com.hero.security;

public enum ApplicationUserAuthority {
    ITEMS_READ("items:read"),
    ITEMS_WRITE("items:write");

    private final String authority;

    ApplicationUserAuthority(String authority) { this.authority = authority; }

    public String getAuthority() { return this.authority; }
}
