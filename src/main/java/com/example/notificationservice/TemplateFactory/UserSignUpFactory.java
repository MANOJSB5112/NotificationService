package com.example.notificationservice.TemplateFactory;

public interface UserSignUpFactory {
     String getFromEmailAddress();
     String getSubjectForUser(String userName,String roleName);

     String getEmailBodyForUser(String userName,String roleName);

}
