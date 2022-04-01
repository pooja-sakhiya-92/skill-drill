package com.skilldrill.registration.utilities.misc;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Properties;
import java.util.TimerTask;

public class EmailUtil extends TimerTask {

    private String toEmail;
    private String subject;
    private String body;

    public EmailUtil(String toEmail, String subject, String body) {
        this.toEmail = toEmail;
        this.subject = subject;
        this.body = body;
    }

    public void sendEmail() {
        try {
            final String fromEmail = "applicationgreenapex@gmail.com";

            final String password="abcdefgh12345678@@";

            Authenticator auth = new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(fromEmail,password);
                }
            };

            Properties properties = new Properties();
            properties.put("mail.smtp.host","smtp.gmail.com");
            properties.put("mail.smtp.port","587");
            properties.put("mail.smtp.auth","true");
            properties.put("mail.smtp.starttls.enable","true");

            Session session = Session.getInstance(properties,auth);
            MimeMessage msg = new MimeMessage(session);

            msg.addHeader("Content-type", "text/HTML; charset=UTF-8");
            msg.addHeader("format", "flowed");
            msg.addHeader("Content-Transfer-Encoding", "8bit");

            msg.setFrom(new InternetAddress(fromEmail,"NoReply-JD"));
            msg.setReplyTo(InternetAddress.parse(toEmail,false));
            msg.setSubject(subject,"UTF-8");
            msg.setText(body,"UTF-8");
            msg.setSentDate(new Date());
            msg.setRecipients(Message.RecipientType.TO,InternetAddress.parse(toEmail,false));
            Transport.send(msg);
            System.out.println("mail sent!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        sendEmail();
    }
}
