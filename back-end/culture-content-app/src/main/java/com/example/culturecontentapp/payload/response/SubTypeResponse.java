package com.example.culturecontentapp.payload.response;
import com.example.culturecontentapp.model.Model;

public class SubTypeResponse extends Model {

    private String name;

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

}
