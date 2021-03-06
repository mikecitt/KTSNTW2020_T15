package com.example.culturecontentapp.payload.request;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class SubTypeRequest {

    private Long id;

    @NotBlank(message = "Name cannot be blank")
    @Size(min = 3, max = 32, message = "Name must be between 3 and 32 characters")
    private String name;

    public SubTypeRequest() {
    }
    public SubTypeRequest(String name) {
        this.name = name;
    }

    public SubTypeRequest(Long dbSubtypeId, String newSubtypeName) {
        this.id = dbSubtypeId;
        this.name = newSubtypeName;
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
