package com.example.culturecontentapp.payload.response;

public class AccountLoginResponse {
    private String token;

    public AccountLoginResponse() {

    }

    public AccountLoginResponse(String token) {
        this.token = token;
    }

    public String getToken() {
        return this.token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
