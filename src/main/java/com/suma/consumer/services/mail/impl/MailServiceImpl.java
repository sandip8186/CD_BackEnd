package com.suma.consumer.services.mail.impl;

import com.suma.consumer.services.mail.Mailservice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.IOException;
import java.util.Date;
import java.util.Properties;

@Service
public class MailServiceImpl implements Mailservice {

    @Value("${spring.mail.host}")
    private String hostName;

    @Value("${spring.mail.port}")
    private String portNumber;

    //  sender userName
    @Value("${spring.mail.username}")
    private String emailId;

    @Value("${spring.mail.password}")
    private String emailPassword;

    @Value("${spring.mail.local.username}")
    private String devEmailId;

    @Value("${spring.mail.local.password}")
    private String devEmailPassword;

    @Value("${spring.mail.properties.mail.smtp.auth}")
    private String smtpAuth;

    @Value("${spring.mail.properties.mail.smtp.starttls.enable}")
    private String starttlsEnable;

    private static final Logger log = LoggerFactory.getLogger(MailServiceImpl.class);

    @Override
    public void mailConfig(String recipient, String mailContent, String mailSubject, String email,String password,String pathForImage) throws MessagingException, IOException {

        Properties props=mailprops();
        // Creates a mail session with the specified properties and an authenticator for user authentication.
        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(email, password);
            }
        });

        /*Creates a new MimeMessage to represent an email message.
         * Setting Email Details:
         * */

        Message msg = new MimeMessage(session);
        //Sets the sender's email address.
        msg.setFrom(new InternetAddress(emailId, false));

        //Sets the recipient's email address.
        msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipient));
        msg.setSubject(mailSubject);
        //Sets the content of the email as HTML.
        msg.setContent(mailContent, "text/html");
        msg.setSentDate(new Date());


        /*Image Attachment:*/
        // Loads the image file specified by pathForImage
        ClassPathResource classPathResource = new ClassPathResource(pathForImage);

        String imagePath = classPathResource.getURL().toString();
        String fileName = imagePath.substring(imagePath.lastIndexOf('/') + 1);

        /* Creates a MimeMultipart object with a subtype of "related." This subtype is typically used for a multipart/related content type,
           which allows you to associate resources (e.g., images) with the main HTML content.*/

        MimeMultipart multipart = new MimeMultipart("related");
        /* : Creates a new MimeBodyPart to represent a part of the MimeMultipart.*/
        BodyPart messageBodyPart = new MimeBodyPart();

        messageBodyPart.setContent(mailContent, "text/html; charset=utf-8");
        multipart.addBodyPart(messageBodyPart);

        //Creates a new MimeBodyPart for the image attachment.
        messageBodyPart = new MimeBodyPart();

        /* Creates a DataSource from the image file specified by imagePath.
           It removes the prefix "file:" from the URL and replaces "%20" with spaces in case there are spaces in the file path.*/

        DataSource fideDataSource = new FileDataSource(imagePath.substring(5).replaceAll("%20"," "));

        messageBodyPart.setDataHandler(new DataHandler(fideDataSource));
        messageBodyPart.setHeader("Content-ID", "<img1>");
        messageBodyPart.setDisposition(MimeBodyPart.INLINE);
        messageBodyPart.setFileName(fileName);
        multipart.addBodyPart(messageBodyPart);
        msg.setContent(multipart);

        // Sends the email using the Transport class.
        Transport.send(msg);

    }

    /*By using Following Method we can Set Up the Email Properties e.g email server set up */
    @Override
    public Properties mailprops() {
        Properties props = new Properties();
        props.put("mail.smtp.auth", smtpAuth);
        props.put("mail.smtp.starttls.enable", starttlsEnable);
        props.put("mail.smtp.host", hostName);
        props.put("mail.smtp.port", portNumber);
        return props;
    }

    @Override
    public Object sendmail(UserDetails userName, String resetURL, String firstName, String otp) throws IOException, MessagingException {

        String mailSubject="CD - Reset Password";
        String email=emailId;
        String password=emailPassword;
        String imagePath="images/logo.png";

        String mailContent = "<html><body> <img style=\"width:550;height:25;\" src=\"cid:img1\">" +
                "<br><br>Dear Customer "+" ,<br><br> You requested to reset your password for Consumer Durable Application." +
                "<br><br>Your reset password OTP (Valid for 10 min)." +
//                "<br><br><a href='"+resetURL+"' target=\"_blank\"><button style=\"background-color:#26a2d0;border:1px;border-radius:2px;width:50px;height:25px;\">Reset</button></a>" +
                "<br><br><span style=\"color: navy; font-weight: bold; font-size: 20px\">" + otp + "</span>" +

                "<br><br><br><br><b>Thanks & Regards,<br>SumaSoft Team<b>"+
                "</body></html>";
        mailConfig("sandip.vargale@sumasoft.net",mailContent,mailSubject,email,password,imagePath);
        mailConfig("sujata.birajdar@sumasoft.net",mailContent,mailSubject,email,password,imagePath);
        log.info("Reset password mail sent to " + userName.getUsername());

        return null;
    }

    @Override
    public Object sendCredMail(String user) throws MessagingException, IOException {
        String mailSubject="CD - Account Credentials";
        String email=emailId;
        String password=emailPassword;
        String imagePath="images/logo.png";
        String resetURL = "http://localhost:8181/user/login";
        String mailContent = "<html><body> <img style=\"width:550;height:25;\" src=\"cid:img1\">" +
                "<br><br>Dear Customer, <br><br> The Password for your CD Application has been changed." +
                "<br><br>If you have to login click the link <a style=\"color: navy;\" href=\"" + resetURL + "\" target=\"_blank\">login</a>" +
                "<br><br><br><b>Thanks & Regards,<br>Sumasoft Team</b>" +
                "</body></html>";

        mailConfig("sandip.vargale@sumasoft.net",mailContent,mailSubject,email,password,imagePath);
        mailConfig("sujata.birajdar@sumasoft.net",mailContent,mailSubject,email,password,imagePath);
        log.info("Credential mail  sent to " + user);
        return null;
    }
}
