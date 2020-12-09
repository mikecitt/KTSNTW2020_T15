package com.example.culturecontentapp.model;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

import javax.persistence.*;

@Entity
public class VerificationToken extends Model {
    private static final int EXPIRATION = 60 * 24;

    @Column(nullable = false)
    private String token;

    @OneToOne(targetEntity = Account.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "account_id")
    private Account account;

    @Column(nullable = false)
    private Date expiryDate;

    private Date calculateExpiryDate(int expiryTimeInMinutes) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Timestamp(cal.getTime().getTime()));
        cal.add(Calendar.MINUTE, expiryTimeInMinutes);
        return new Date(cal.getTime().getTime());
    }

    public VerificationToken() {
    }

    public VerificationToken(String token, Account account) {
        this.token = token;
        this.account = account;
        this.expiryDate = calculateExpiryDate(EXPIRATION);
    }

    public String getToken() {
        return this.token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Account getAccount() {
        return this.account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }
}