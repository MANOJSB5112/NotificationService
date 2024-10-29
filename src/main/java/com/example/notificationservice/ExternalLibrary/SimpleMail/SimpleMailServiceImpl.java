package com.example.notificationservice.ExternalLibrary.SimpleMail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import java.util.Properties;

@Component
public class SimpleMailServiceImpl implements SimpleMailService{
    private SimpleMailUtil simpleMailUtil;

    @Autowired
    public SimpleMailServiceImpl(SimpleMailUtil simpleMailUtil)
    {
        this.simpleMailUtil=simpleMailUtil;
    }
    @Override
    public void sendEmail(String toEmail, String fromEmail,String emailSubject, String emailBody) {
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com"); //SMTP Host
        props.put("mail.smtp.port", "587"); //TLS Port
        props.put("mail.smtp.auth", "true"); //enable authentication
        props.put("mail.smtp.starttls.enable", "true"); //enable STARTTLS


        //create Authenticator object to pass in Session.getInstance argument
        Authenticator auth = new Authenticator() {
            //override the getPasswordAuthentication method
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(fromEmail, "cfokvscoamywiyai");
            }
        };
        Session session = Session.getInstance(props, auth);

        simpleMailUtil.sendEmail(session, toEmail,fromEmail,emailSubject, emailBody);
    }
}
