package com.example.culturecontentapp.payload.request;

import com.example.culturecontentapp.model.Model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class TypeRequest {

    private Long id;

    @NotBlank(message = "Name cannot be blank")
    @Size(min = 3, max = 32, message = "Name must be between 3 and 32 characters")
    private String name;

    public TypeRequest() { }

    public TypeRequest(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
