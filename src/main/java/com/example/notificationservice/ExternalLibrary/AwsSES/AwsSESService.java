package com.example.notificationservice.ExternalLibrary.AwsSES;

public interface AwsSESService {
    void sendEmail(String toEmail, String fromEmail,String emailSubject, String emailBody);
}
