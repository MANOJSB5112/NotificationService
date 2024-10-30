package com.example.notificationservice.ExternalLibrary.AwsSES;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.ses.SesClient;
import software.amazon.awssdk.services.ses.model.*;

@Service
@Primary
public class AwsSESServiceImpl implements AwsSESService{
    private SesClient sesClient;

    @Autowired
    public AwsSESServiceImpl(SesClient sesClient)
    {
        this.sesClient=sesClient;
    }
    @Override
    public void sendEmail(String toEmail, String fromEmail, String emailSubject, String emailBody) {
        try {
            // Build the email request
            SendEmailRequest emailRequest = SendEmailRequest.builder()
                    .destination(Destination.builder().toAddresses(toEmail).build())
                    .message(Message.builder()
                            .subject(Content.builder().data(emailSubject).build())
                            .body(Body.builder()
                                    .text(Content.builder().data(emailBody).build())
                                    .build())
                            .build())
                    .source(fromEmail)
                    .build();

            // Send the email
            sesClient.sendEmail(emailRequest);
            System.out.println("Email sent successfully");

        } catch (SesException e) {
            System.err.println(e.awsErrorDetails().errorMessage());
        }
    }
}
