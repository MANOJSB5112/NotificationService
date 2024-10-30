package com.example.notificationservice.EmailAdapter;

import com.example.notificationservice.ExternalLibrary.AwsSES.AwsSESService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
@Primary
public class AwsSESEmailProcessor implements EmailProcessor{
    private AwsSESService awsSESService;

    @Autowired
    public AwsSESEmailProcessor(AwsSESService awsSESService)
    {
        this.awsSESService=awsSESService;
    }

    @Override
    public void sendEmail(String toEmail, String fromEmail, String emailSubject, String emailBody) {
        awsSESService.sendEmail(toEmail,fromEmail,emailSubject,emailBody);
    }
}
