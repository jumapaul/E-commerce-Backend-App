package com.ecommerceapp.notificationservice.email;

import com.ecommerceapp.notificationservice.notification.dto.OrderConfirmation;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.util.HashMap;
import java.util.Map;

import static com.ecommerceapp.notificationservice.email.EmailTemplate.ORDER_CONFIRMATION_EMAIL;
import static com.ecommerceapp.notificationservice.email.EmailTemplate.VERIFICATION_CODE;

@Service
@RequiredArgsConstructor
public class EmailService {
    private final JavaMailSender javaMailSender;
    private final SpringTemplateEngine templateEngine;

    @Async
    public void sendOrderConfirmationMail(
            String destinationEmail,
            OrderConfirmation orderConfirmation
    ) throws MessagingException {

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);

        messageHelper.setFrom("noreply@gmail.com");

        final String templateName = ORDER_CONFIRMATION_EMAIL.getTemplate();

        Map<String, Object> variables = new HashMap<>();

        variables.put("status", orderConfirmation.status());
        variables.put("orderId", orderConfirmation.id());
        variables.put("totalPrice", orderConfirmation.totalPrice());
        variables.put("cartItems", orderConfirmation.cartItems());

        Context context = new Context();
        context.setVariables(variables);
        messageHelper.setSubject(ORDER_CONFIRMATION_EMAIL.getSubject());

        try {
            String htmlTemplate = templateEngine.process(templateName, context);
            messageHelper.setText(htmlTemplate, true);
            messageHelper.setTo(destinationEmail);
            javaMailSender.send(mimeMessage);
        } catch (MessagingException exception) {
            throw new MessagingException("Error sending the email");
        }
    }

    @Async
    public void sendVerificationCode(
            String destinationEmail,
            String verificationCode
    ) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
        messageHelper.setFrom("noreply@gmail.com");

        final String templateName = VERIFICATION_CODE.getTemplate();

        Map<String, Object> variables = new HashMap<>();

        variables.put("code", verificationCode);

        Context context = new Context();
        context.setVariables(variables);
        messageHelper.setSubject(VERIFICATION_CODE.getSubject());

        try {
            String htmlTemplate = templateEngine.process(templateName, context);
            messageHelper.setText(htmlTemplate, true);
            messageHelper.setTo(destinationEmail);
            javaMailSender.send(mimeMessage);
        }catch (MessagingException exception){
            throw new MessagingException("Error sending the mail");
        }
    }
}
