package com.ecommerceapp.authenticationservice.email;

//import jakarta.mail.MessagingException;
//import jakarta.mail.internet.MimeMessage;

import com.mailersend.sdk.MailerSend;
import com.mailersend.sdk.emails.Email;
import com.mailersend.sdk.exceptions.MailerSendException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.messaging.MessagingException;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.util.HashMap;
import java.util.Map;

import static com.ecommerceapp.authenticationservice.email.EmailTemplates.VERIFICATION_CODE;
import static java.nio.charset.StandardCharsets.UTF_8;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailService {

    //    private final JavaMailSender javaMailSender;
    private final SpringTemplateEngine templateEngine;

    @Value("${spring.mail.access_token}")
    private String accessToken;

    @Value("${spring.mail.from_mail}")
    private String fromMail;

    @Async
    public void sendEmailVerificationCode(
            String destinationEmail,
            String verificationCode
    ) {

        Email email = new Email();
        email.setFrom("Ecommerce", fromMail);
        email.addRecipient("Customer", destinationEmail);

        final String templateName = VERIFICATION_CODE.getTemplate();
        Map<String, Object> variables = new HashMap<>();
        variables.put("verificationCode", verificationCode);
        Context context = new Context();
        context.setVariables(variables);
        email.setSubject(VERIFICATION_CODE.getSubject());

        String htmlTemplate = templateEngine.process(templateName, context);
        email.setHtml(htmlTemplate);
        MailerSend mailerSend = new MailerSend();
        mailerSend.setToken(accessToken);

        try {
            mailerSend.emails().send(email);
        } catch (MailerSendException e) {
            log.info("=================> {}:", e.message);
            throw new MessagingException(e.getMessage());
        }

//        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
//        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, MimeMessageHelper.MULTIPART_MODE_RELATED, UTF_8.name());
//
//        messageHelper.setFrom("noreply@gmail.com");
//        final String templateName = VERIFICATION_CODE.getTemplate();
//
//        Map<String, Object> variables = new HashMap<>();
//
//        variables.put("verificationCode", verificationCode);
//
//        Context context = new Context();
//        context.setVariables(variables);
//        messageHelper.setSubject(VERIFICATION_CODE.getSubject());

//        try {
//            String htmlTemplate = templateEngine.process(templateName, context);
//            messageHelper.setText(htmlTemplate, true);
//            messageHelper.setTo(destinationEmail);
//            javaMailSender.send(mimeMessage);
//        } catch (MessagingException exception) {
//            throw new MessagingException("Error sending the email");
//        }
    }
}
