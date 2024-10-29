package com.example.notificationservice.TemplateFactory;

public interface UserSignUpFactory {
     String getFromEmailAddress();
     String getSubjectForUser(String userName);

     String getEmailBodyForUser(String userName);

}
