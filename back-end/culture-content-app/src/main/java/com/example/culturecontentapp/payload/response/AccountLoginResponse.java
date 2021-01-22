package com.example.culturecontentapp.payload.response;

public class AccountLoginResponse {
    private String token;
    private String role;
    private String email;

    public AccountLoginResponse() {

    }

    public AccountLoginResponse(String token, String role, String email) {
        this.token = token;
        this.role = role;
        this.email = email;
    }

    public String getToken() {
        return this.token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getRole() {
        return this.role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getEmail() {
        return this.email;
    }
}
