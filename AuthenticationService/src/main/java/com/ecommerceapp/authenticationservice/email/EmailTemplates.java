package com.ecommerceapp.authenticationservice.email;

import lombok.Getter;

@Getter
public enum EmailTemplates {
    VERIFICATION_CODE("verification-code.html", "Email verification code send");

    private final String template;

    private final String subject;

    EmailTemplates(String template, String subject) {
        this.template = template;
        this.subject = subject;
    }
}
