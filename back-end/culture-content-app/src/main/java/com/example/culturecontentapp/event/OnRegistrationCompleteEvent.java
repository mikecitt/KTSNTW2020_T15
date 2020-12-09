package com.example.culturecontentapp.event;

import java.util.Locale;

import com.example.culturecontentapp.model.Account;

import org.springframework.context.ApplicationEvent;

public class OnRegistrationCompleteEvent extends ApplicationEvent {
    private String appUrl;
    private Locale locale;
    private Account account;

    public OnRegistrationCompleteEvent(Account account, Locale locale, String appUrl) {
        super(account);

        this.account = account;
        this.locale = locale;
        this.appUrl = appUrl;
    }

    public String getAppUrl() {
        return this.appUrl;
    }

    public void setAppUrl(String appUrl) {
        this.appUrl = appUrl;
    }

    public Locale getLocale() {
        return this.locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    public Account getAccount() {
        return this.account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

}
