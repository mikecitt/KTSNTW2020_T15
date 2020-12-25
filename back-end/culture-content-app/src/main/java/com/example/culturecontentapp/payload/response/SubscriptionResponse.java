package com.example.culturecontentapp.payload.response;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.*;

public class SubscriptionResponse {

    private Long id;

    private String culturalOfferName;

    @NotBlank(message = "Text cannot be blank")
    @Size(min = 10, max = 256, message = "Description must be between 10 and 256 characters")
    private String text;

    @NotNull(message = "Date cannot be null")
    private LocalDateTime date;

    private Set<String> images;

    public SubscriptionResponse() {
        images = new HashSet<>();
    }

    public Long getId() {
        return this.id;
    }

    public String getCulturalOfferName() {
        return this.culturalOfferName;
    }

    public void setCulturalOfferName(String culturalOfferName) {
        this.culturalOfferName = culturalOfferName;
    }

    public String getText() {
        return this.text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public LocalDateTime getDate() {
        return this.date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public Set<String> getImages() {
        return this.images;
    }
}
