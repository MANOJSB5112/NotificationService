package com.example.notificationservice.TemplateFactory;


import org.springframework.stereotype.Component;

@Component
public class UserSignUpFactoryImpl implements UserSignUpFactory {

    @Override
    public String getFromEmailAddress() {
        return "manojsb5112@gmail.com";
    }

    @Override
    public String getSubjectForUser(String userName,String roleName) {
        return String.format("%s SignedUp as %s ",userName,roleName);
    }

    @Override
    public String getEmailBodyForUser(String userName,String roleName) {
       String body=String.format( """
           Hi %s !
           
           Welcome to ZStore,
           
           Thanks for Signing up as %s!
           
           Hope you will have a great shopping experience !
            
           Team ZStore
           """,userName,roleName);
       return body;
    }
}
