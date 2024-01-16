package com.suma.consumer.services.mail;

import org.springframework.security.core.userdetails.UserDetails;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.Properties;

public interface Mailservice {

    void mailConfig(String recipient, String mailContent, String mailSubject, String email,String password,String imagePath) throws MessagingException, IOException;

    Properties mailprops();

    Object sendmail(UserDetails userDetails, String resetURL, String firstName , String otp) throws IOException, MessagingException;

    Object sendCredMail(String user) throws MessagingException, IOException ;
}
