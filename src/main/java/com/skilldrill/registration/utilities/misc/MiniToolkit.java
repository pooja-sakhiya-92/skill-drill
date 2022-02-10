package com.skilldrill.registration.utilities.misc;
/*
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class MiniToolkit {
    public static boolean mailingTool(String email, String password, String recieverEmail, String messageSubject, String messageBody) {
        boolean flag = false;
        String host = "smtp.gmail.com";
        Properties properties = System.getProperties();
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(email, password);
            }
        });
        session.setDebug(true);
        MimeMessage mimeMessage = new MimeMessage(session);
        try {
            mimeMessage.setFrom(email);
            mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(recieverEmail));
            mimeMessage.setSubject(messageSubject);
            mimeMessage.setText(messageBody);
            Transport.send(mimeMessage);
            flag = true;
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return flag;
    }

}*/

