package com.example.culturecontentapp.payload.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class AccountRegisterRequest {

    @Email(message = "Email must be valid.")
    @Size(max = 50, message = "Email cannot have more than 50 characters")
    protected String email;

    @NotBlank(message = "Username cannot be blank")
    @Size(min = 5, max = 64, message = "Username must have between 5 and 64 characters")
    private String username;

    @NotBlank(message = "Password cannot be blank")
    @Size(max = 60, message = "Password cannot have more than 60 characters")
    protected String password;

    public AccountRegisterRequest() {
    }

    public AccountRegisterRequest(String email, String username, String password) {
        this.email = email;
        this.username = username;
        this.password = password;
    }

    public String getEmail() {
        return this.email;
    }

    public String getUsername() {
        return this.username;
    }

    public String getPassword() {
        return this.password;
    }
}
