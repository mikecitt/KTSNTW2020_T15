package com.example.culturecontentapp.payload.request;

public class AddReviewRequest {

    private Short rating;

    private String comment;

    public AddReviewRequest() {
    }

    public AddReviewRequest(Short rating, String comment) {
        this.rating = rating;
        this.comment = comment;
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
}
