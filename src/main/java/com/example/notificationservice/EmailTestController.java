package com.example.notificationservice;

import com.example.notificationservice.EmailAdapter.EmailProcessor;
import com.example.notificationservice.TemplateFactory.UserSignUpFactory;
import com.example.notificationservice.dto.SendEmailDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/sendEmail")
public class EmailTestController {
    private EmailProcessor emailProcessor;
    private UserSignUpFactory userSignUpFactory;

    @Autowired
    public EmailTestController (EmailProcessor emailProcessor,UserSignUpFactory userSignUpFactory)
    {
        this.emailProcessor=emailProcessor;
        this.userSignUpFactory=userSignUpFactory;
    }
    @PostMapping("/send")
    public ResponseEntity<String> sendEmail(@RequestBody SendEmailDto emailDto)
    {
        String userName=emailDto.getName();
        String to =emailDto.getEmail();
        String from= userSignUpFactory.getFromEmailAddress();
        String subject=userSignUpFactory.getSubjectForUser(userName);
        String body=userSignUpFactory.getEmailBodyForUser(userName);
        emailProcessor.sendEmail(to,from,subject,body);
        return ResponseEntity.ok("Email sent Successfully");
    }
}
