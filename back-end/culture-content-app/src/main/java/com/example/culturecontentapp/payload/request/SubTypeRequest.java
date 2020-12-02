package com.example.culturecontentapp.payload.request;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class SubTypeRequest {

    @NotBlank(message = "Name cannot be blank")
    @Size(min = 3, max = 32, message = "Name must be between 3 and 32 characters")
    private String name;

    public SubTypeRequest() {
    }
    public SubTypeRequest(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
