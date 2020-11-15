package com.hero.security;

public enum ApplicationUserAuthority {
    ITEMS_READ("item:read"),
    ITEMS_WRITE("item:write");

    private final String authority;

    ApplicationUserAuthority(String authority) { this.authority = authority; }

    public String getAuthority() { return this.authority; }
}
