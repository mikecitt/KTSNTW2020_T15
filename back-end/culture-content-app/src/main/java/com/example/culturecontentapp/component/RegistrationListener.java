package com.example.culturecontentapp.component;

import java.util.UUID;

import com.example.culturecontentapp.event.OnRegistrationCompleteEvent;
import com.example.culturecontentapp.model.Account;
import com.example.culturecontentapp.service.VerificationTokenService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class RegistrationListener implements ApplicationListener<OnRegistrationCompleteEvent> {

    @Autowired
    private VerificationTokenService service;

    @Autowired
    private JavaMailSender mailSender;

    @Override
    public void onApplicationEvent(OnRegistrationCompleteEvent event) {
        this.confirmRegistration(event);
    }

    private void confirmRegistration(OnRegistrationCompleteEvent event) {
        Account account = event.getAccount();
        String token = UUID.randomUUID().toString();
        service.createVerificationToken(account, token);

        String recipientAddress = account.getEmail();
        String subject = "Potvrda registracije";
        String confirmationUrl = event.getAppUrl() + "/auth/activate?token=" + token;
        String message = "Za potvrdu registracije otvorite sledeci link: ";

        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(recipientAddress);
        email.setSubject(subject);
        email.setText(message + "\r\n" + "http://localhost:8080" + confirmationUrl);
        mailSender.send(email);
    }
}
