package com.trade.tradelicense.application.validators;

import com.trade.tradelicense.application.commands.AttachTradeLicenseApplicationDocumentCommand;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class AttachDocumentValidator {
    public void validate(AttachTradeLicenseApplicationDocumentCommand command) {
        Objects.requireNonNull(command, "Command is required");
        if (command.applicationId() == null) {
            throw new IllegalArgumentException("Application id is required");
        }
        requireText(command.documentType(), "Document type is required");
        requireText(command.documentReference(), "Document reference is required");
    }

    private void requireText(String value, String message) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(message);
        }
    }
}
