package com.example.culturecontentapp.payload.response;
import com.example.culturecontentapp.model.Model;
import com.example.culturecontentapp.model.SubType;

public class TypeResponse extends Model {

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

}
