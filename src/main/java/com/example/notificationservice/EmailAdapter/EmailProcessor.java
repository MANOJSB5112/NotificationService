package com.example.notificationservice.EmailAdapter;

public interface EmailProcessor {
    void sendEmail(String toEmail, String fromEmail,String emailSubject, String emailBody);
}
