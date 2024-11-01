package com.example.notificationservice.Kafka;

import com.example.notificationservice.EmailAdapter.EmailProcessor;
import com.example.notificationservice.TemplateFactory.UserSignUpFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
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

//    @KafkaListener(topics= "user-signup-event", groupId = "notificationService")
//    public void handleNewUserSignUpEvent(String message) throws JsonProcessingException {
//        NewUserExternalDto newUserExternalDto=objectMapper.readValue(message, NewUserExternalDto.class);
//        String userName=newUserExternalDto.getName();
//        String to =newUserExternalDto.getEmail();
//        String roleName=newUserExternalDto.getRoleName();
//        String from= userSignUpFactory.getFromEmailAddress();
//        String subject=userSignUpFactory.getSubjectForUser(userName,roleName);
//        String body=userSignUpFactory.getEmailBodyForUser(userName,roleName);
//        emailProcessor.sendEmail(to,from,subject,body);
//    }
}
