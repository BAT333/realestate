package com.example.realestate.domain;

public enum UserRole {
    ADMIN("admin"),
    USER("user"),
    SUB("sub");
    private final String values;
   UserRole(String values){
        this.values = values;
    }

    public String getValues() {
        return values;
    }
}
