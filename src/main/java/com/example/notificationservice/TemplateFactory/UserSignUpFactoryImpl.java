package com.example.notificationservice.TemplateFactory;


import org.springframework.stereotype.Component;

@Component
public class UserSignUpFactoryImpl implements UserSignUpFactory {

    @Override
    public String getFromEmailAddress() {
        return "manojsb5112@gmail.com";
    }

    @Override
    public String getSubjectForUser(String userName) {
        return String.format("%s Welcome to ZStore ",userName);
    }

    @Override
    public String getEmailBodyForUser(String userName) {
       String body=String.format( """
           Hi %s !
           
           Thanks for Signing up !
           
           Hope you will have a great shopping experience !
            
           Team ZStore
           """,userName);
       return body;
    }
}
