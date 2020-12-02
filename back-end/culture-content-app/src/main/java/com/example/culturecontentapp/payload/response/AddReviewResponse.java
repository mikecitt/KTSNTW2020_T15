package com.example.culturecontentapp.payload.response;

import java.util.HashSet;
import java.util.Set;

public class AddReviewResponse {
    private Long id;
    private Short rating;
    private String comment;
    private String authorUsername;
    private Set<String> images;

    public AddReviewResponse() {
        images = new HashSet<>();
    }

    public AddReviewResponse(Long id, Short rating, String comment, String authorUsername) {
        this.id = id;
        this.rating = rating;
        this.comment = comment;
        this.authorUsername = authorUsername;
        images = new HashSet<>();
    }

    public long getId() {
        return this.id;
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

    public String getAuthorUsername() {
        return this.authorUsername;
    }

    public void setAuthorUsername(String authorUsername) {
        this.authorUsername = authorUsername;
    }

    public Set<String> getImages() {
        return this.images;
    }
}
