package com.getyourway.user;

public class UserDTO {
    private String username;
    private String password;
    private String roles;

    public UserDTO(String username, String password, String roles) {
        this.username = username;
        this.password = password;
        this.roles = roles;
    }

    public String getUsername() {
        return this.username;
    }

    public String getPassword() {
        return this.password;
    }

    public String getRoles() {
        return this.roles;
    }
}
