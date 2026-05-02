package com.trade.tradelicense.application.validators;

import com.trade.tradelicense.application.commands.ReviewTradeLicenseApplicationCommand;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class ReviewApplicationValidator {
    public void validate(ReviewTradeLicenseApplicationCommand command) {
        Objects.requireNonNull(command, "Command is required");
        requireNonNull(command.applicationId(), "Application id is required");
        requireNonNull(command.reviewerId(), "Reviewer id is required");
        requireNonNull(command.role(), "Reviewer role is required");
        requireNonNull(command.decision(), "Review decision is required");
        requireText(command.comment(), "Review comment is required");
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
