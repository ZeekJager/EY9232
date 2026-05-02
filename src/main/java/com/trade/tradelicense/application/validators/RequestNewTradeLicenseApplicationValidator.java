package com.trade.tradelicense.application.validators;

import com.trade.tradelicense.application.commands.RequestNewTradeLicenseApplicationCommand;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class RequestNewTradeLicenseApplicationValidator {
    public void validate(RequestNewTradeLicenseApplicationCommand command) {
        Objects.requireNonNull(command, "Command is required");
        requireNonNull(command.applicantId(), "Applicant id is required");
        requireText(command.fullName(), "Full name is required");
        requireText(command.nationalIdNumber(), "National id number is required");
        requireText(command.tinNumber(), "TIN number is required");
        requireText(command.email(), "Email is required");
        requireText(command.phoneNumber(), "Phone number is required");
        requireText(command.tradeLicenseType(), "Trade license type is required");
        requireText(command.commodity(), "Commodity is required");
    }

    private void requireNonNull(Object value, String message) {
        if (value == null) {
            throw new IllegalArgumentException(message);
        }
    }

    private void requireText(String value, String message) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(message);
        }
    }
}
