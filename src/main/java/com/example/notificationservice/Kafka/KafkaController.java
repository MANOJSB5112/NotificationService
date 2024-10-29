package com.example.notificationservice.Kafka;

import com.example.notificationservice.EmailAdapter.EmailProcessor;
import com.example.notificationservice.TemplateFactory.UserSignUpFactory;
import com.example.notificationservice.dto.SendEmailDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Controller;

@Controller
public class KafkaController {
    private ObjectMapper objectMapper;
    private EmailProcessor emailProcessor;
    private UserSignUpFactory userSignUpFactory;

    @Autowired
    public KafkaController(ObjectMapper objectMapper,EmailProcessor emailProcessor,UserSignUpFactory userSignUpFactory)
    {
        this.objectMapper=objectMapper;
        this.emailProcessor=emailProcessor;
        this.userSignUpFactory=userSignUpFactory;
    }

    @KafkaListener(topics= "user-signup-event", groupId = "notificationService")
    public void handleNewUserSignUpEvent(String message) throws JsonProcessingException {
        SendEmailDto emailDto=objectMapper.readValue(message,SendEmailDto.class);
        String userName=emailDto.getUserName();
        String to =emailDto.getTo();
        String from= userSignUpFactory.getFromEmailAddress();
        String subject=userSignUpFactory.getSubjectForUser(userName);
        String body=userSignUpFactory.getEmailBodyForUser(userName);
        emailProcessor.sendEmail(to,from,subject,body);
    }
}
