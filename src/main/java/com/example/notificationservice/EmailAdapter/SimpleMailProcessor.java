package com.example.notificationservice.EmailAdapter;

import com.example.notificationservice.ExternalLibrary.SimpleMail.SimpleMailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SimpleMailProcessor implements EmailProcessor{
    private SimpleMailService simpleMailService;

    @Autowired
    public SimpleMailProcessor(SimpleMailService simpleMailService)
    {
        this.simpleMailService=simpleMailService;
    }

    @Override
    public void sendEmail(String toEmail, String fromEmail,String emailSubject, String emailBody) {
        simpleMailService.sendEmail(toEmail,fromEmail,emailSubject,emailBody);
    }
}
