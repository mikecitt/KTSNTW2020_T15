package com.example.culturecontentapp.payload.request;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class ReviewRequest {

    @NotNull(message = "Rating cannot be null")
    @Min(1)
    @Max(5)
    private Short rating;

    @NotBlank(message = "Comment cannot be blank")
    @Size(min = 10, max = 256, message = "Description must be between 10 and 256 characters")
    private String comment;

    private String[] images;

    public ReviewRequest() {
    }

    public ReviewRequest(Short rating, String comment, String[] images) {
        this.rating = rating;
        this.comment = comment;
        this.images = images;
    }

    public ReviewRequest(Short rating, String comment) {
        this.rating = rating;
        this.comment = comment;
        this.images = new String[0];
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

    public String[] getImages() {
        return this.images;
    }
}
