package com.example.notificationservice.ExternalLibrary.Twilio;

public interface TwilioApiService {
    void sendEmail(String toEmail, String fromEmail,String emailSubject, String emailBody);
}
