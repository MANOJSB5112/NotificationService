package com.example.notificationservice.AwsSQS;

import com.example.notificationservice.EmailAdapter.EmailProcessor;
import com.example.notificationservice.TemplateFactory.UserSignUpFactory;
import com.example.notificationservice.dto.NewUserExternalDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.aws.messaging.listener.annotation.SqsListener;
import org.springframework.stereotype.Controller;

@Controller
public class AwsSQSController {
    private ObjectMapper objectMapper;
    private EmailProcessor emailProcessor;
    private UserSignUpFactory userSignUpFactory;

    @Autowired
    public AwsSQSController(ObjectMapper objectMapper,EmailProcessor emailProcessor,UserSignUpFactory userSignUpFactory)
    {
        this.objectMapper=objectMapper;
        this.emailProcessor=emailProcessor;
        this.userSignUpFactory=userSignUpFactory;
    }

    @SqsListener("notify-newuser-queue")
    public void handleNewUserSignUpEvent(String snsMessage) throws JsonProcessingException {
        System.out.println("Raw message received: " + snsMessage);

        // Parse the outer SNS message
        JsonNode snsJsonNode = objectMapper.readTree(snsMessage);

        // Extract the inner "Message" field, which contains the JSON payload
        String message = snsJsonNode.get("Message").asText();

        // Deserialize the inner JSON into SendEmailDto
        NewUserExternalDto newUserExternalDto = objectMapper.readValue(message, NewUserExternalDto.class);

        String userName = newUserExternalDto.getName();
        String to = newUserExternalDto.getEmail();
        String roleName=newUserExternalDto.getRoleName();
        String from = userSignUpFactory.getFromEmailAddress();
        String subject = userSignUpFactory.getSubjectForUser(userName,roleName);
        String body = userSignUpFactory.getEmailBodyForUser(userName,roleName);

        System.out.println("Received here : " + userName + " " + to + " " + from);
        emailProcessor.sendEmail(to, from, subject, body);
    }
}
