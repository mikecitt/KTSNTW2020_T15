package com.example.culturecontentapp.component;

import com.example.culturecontentapp.event.OnNewsCreatedEvent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class NewsCreatedListener implements ApplicationListener<OnNewsCreatedEvent>{

    @Autowired
    private JavaMailSender mailSender;

    @Override
    public void onApplicationEvent(OnNewsCreatedEvent event) {
        this.sendNewsletter(event);
    }

    private void sendNewsletter(OnNewsCreatedEvent event){
        SimpleMailMessage email = new SimpleMailMessage();
        for(String emailAddress : event.getUserEmails()){
            email.setTo(emailAddress);
            email.setSubject("Vest za kulturnu ponudu -" + event.getCulturalOfferName());
            email.setText(event.getNews().getText() + "\n" + event.getNews().getDate());
            mailSender.send(email);
        }
    }

    
    
}
