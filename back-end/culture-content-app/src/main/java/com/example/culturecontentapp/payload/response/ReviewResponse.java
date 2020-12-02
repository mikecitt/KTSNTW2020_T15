package com.example.culturecontentapp.payload.response;

import java.util.HashSet;
import java.util.Set;

public class ReviewResponse {
    private Short rating;
    private String comment;
    private String author;
    private Set<String> images;

    public ReviewResponse() {
        images = new HashSet<>();
    }

    public ReviewResponse(Short rating, String comment, String author) {
        this.rating = rating;
        this.comment = comment;
        this.author = author;
        images = new HashSet<>();
    }

    public Short getRating() {
        return this.rating;
    }

    public void setRating(Short rating) {
        this.rating = rating;
    }

    public String getComment() {
        return this.comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getAuthor() {
        return this.author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Set<String> getImages() {
        return this.images;
    }
}
