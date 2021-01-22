package com.example.culturecontentapp.payload.response;
import com.example.culturecontentapp.model.Model;

public class SubTypeResponse {

    private Long id;

    private String name;

    private TypeResponse type;

    public SubTypeResponse() {
    }

    public SubTypeResponse(String name) {
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

    public TypeResponse getType(){return type;}

    public void seType(TypeResponse type){
        this.type = type;
    }
}
