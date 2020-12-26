package com.example.culturecontentapp.event;

import java.util.List;

import com.example.culturecontentapp.model.News;

import org.springframework.context.ApplicationEvent;

public class OnNewsCreatedEvent extends ApplicationEvent {

    private static final long serialVersionUID = 1L;
    private News news;
    private String culturalOfferName;
    private List<String> userEmails;

    public OnNewsCreatedEvent(News news, String culturalOfferName, List<String> userEmails) {
        super(news);
        this.news = news;
        this.culturalOfferName = culturalOfferName;
        this.userEmails = userEmails;
    }

    public void setNews(News news){
        this.news = news;
    }

    public News getNews(){
        return news;
    }

    public void setCulturalOfferName(String name){
        this.culturalOfferName = name;
    }

    public String getCulturalOfferName(){
        return this.culturalOfferName;
    }

    public void setUserEmails(List<String> userEmails){
        this.userEmails = userEmails;
    }

    public List<String> getUserEmails(){
        return this.userEmails;
    }


    
}
