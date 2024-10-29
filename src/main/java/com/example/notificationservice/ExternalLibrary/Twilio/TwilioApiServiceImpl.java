package com.example.notificationservice.ExternalLibrary.Twilio;

import org.springframework.stereotype.Component;

@Component
public class TwilioApiServiceImpl implements TwilioApiService{


    @Override
    public void sendEmail(String toEmail, String fromEmail, String emailSubject, String emailBody) {

    }
}
