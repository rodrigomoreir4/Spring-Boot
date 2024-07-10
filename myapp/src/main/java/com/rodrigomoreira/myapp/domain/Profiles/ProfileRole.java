package com.rodrigomoreira.myapp.domain.Profiles;

public enum ProfileRole {

    MASTER("master"),
    
    ADMIN("admin"),

    USER("user");

    private String role;

    ProfileRole(String role){
        this.role = role;
    }

    public String getRole(){
        return role;
    }
}
