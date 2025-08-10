package com.ecommerceapp.notificationservice.email;

import lombok.Getter;

public enum EmailTemplate {
    ORDER_CONFIRMATION_EMAIL("order-confirmation-mail.html", "Order Confirmation"),
    VERIFICATION_CODE("verification-code.html", "Email verification code send");

    @Getter
    private final String template;

    @Getter
    private final String subject;

    EmailTemplate(String template, String subject) {
        this.template = template;
        this.subject = subject;
    }
}
