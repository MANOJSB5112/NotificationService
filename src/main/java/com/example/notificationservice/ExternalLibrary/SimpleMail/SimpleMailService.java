package com.example.notificationservice.ExternalLibrary.SimpleMail;

public interface SimpleMailService {
    void sendEmail(String toEmail, String fromEmail,String emailSubject, String emailBody);
}
