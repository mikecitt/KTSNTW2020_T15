package com.example.culturecontentapp.payload.response;

public class TypeResponse {

    private Long id;

    private String name;

    public TypeResponse() { }

    public TypeResponse(String name) {
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
