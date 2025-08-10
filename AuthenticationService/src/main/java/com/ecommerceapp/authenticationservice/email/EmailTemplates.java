package com.ecommerceapp.authenticationservice.email;

import lombok.Getter;

public enum EmailTemplates {
    VERIFICATION_CODE("verification-code.html", "Email verification code send");

    @Getter
    private final String template;

    @Getter
    private final String subject;

    EmailTemplates(String template, String subject) {
        this.template = template;
        this.subject = subject;
    }
}
