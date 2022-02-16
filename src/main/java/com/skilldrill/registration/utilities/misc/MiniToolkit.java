package com.skilldrill.registration.utilities.misc;

import com.skilldrill.registration.constants.AllPurposeConstants;
import com.skilldrill.registration.dto.UserInfoDto;
import com.twilio.Twilio;
import com.twilio.rest.verify.v2.service.Verification;
import com.twilio.rest.verify.v2.service.VerificationCheck;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Component
public class MiniToolkit {

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private SpringTemplateEngine templateEngine;
    
    @Value("${twilio.account_sid}")
    private String ACCOUNT_SID;

    @Value("${twilio.auth_token}")
    private String AUTH_TOKEN;

    @Value("${twilio.path_service_sid}")
    private String PATH_SERVICE_SID;
    private String recipientEmail;

    public void sendAcknowledgement(UserInfoDto userInfoDto) throws MessagingException, IOException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message,
                MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                StandardCharsets.UTF_8.name());
        Context context = new Context();
        context.setVariable("fname", userInfoDto.getFname());
        context.setVariable("isactive", userInfoDto.getIsactive());

        String html = templateEngine.process("mailTemplate", context);
        helper.setTo(userInfoDto.getEmail());
        helper.setText(html, true);
        helper.setSubject(AllPurposeConstants.VERIFICATION_ACKNOWLEDGEMENT);
        javaMailSender.send(message);
    }

    public void sendEmailVerificationOTP(String email) {
        recipientEmail = email;
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        Verification verification = Verification.creator(
                        PATH_SERVICE_SID,
                        recipientEmail,
                        "email")
                .create();

        System.out.println(verification.getSid());
    }

    public Boolean verifyEmailOTP(String recipientEmail, String userOtp) {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        VerificationCheck verificationCheck = VerificationCheck.creator(
                        PATH_SERVICE_SID,
                        userOtp)
                .setTo(recipientEmail).create();
        return verificationCheck.getStatus().equals("approved");
    }
}

