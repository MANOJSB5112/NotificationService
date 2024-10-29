package com.example.notificationservice.EmailAdapter;

import com.example.notificationservice.ExternalLibrary.Twilio.TwilioApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TwilioEmailProcessor implements EmailProcessor {
    private TwilioApiService twilioApiService;

    @Autowired
    public TwilioEmailProcessor(TwilioApiService twilioApiService)
    {
        this.twilioApiService=twilioApiService;
    }
    @Override
    public void sendEmail(String toEmail, String fromEmail,String emailSubject, String emailBody) {
        twilioApiService.sendEmail(toEmail,fromEmail,emailSubject,emailBody);
    }
}
