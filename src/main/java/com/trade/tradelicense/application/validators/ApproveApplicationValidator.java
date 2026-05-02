package com.trade.tradelicense.application.validators;

import com.trade.tradelicense.domain.enums.ApprovalDecision;
import com.trade.tradelicense.application.commands.ApproveTradeLicenseApplicationCommand;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class ApproveApplicationValidator {
    public void validate(ApproveTradeLicenseApplicationCommand command) {
        Objects.requireNonNull(command, "Command is required");
        requireNonNull(command.applicationId(), "Application id is required");
        requireNonNull(command.approverId(), "Approver id is required");
        requireNonNull(command.role(), "Approver role is required");
        requireNonNull(command.decision(), "Approval decision is required");
        requireText(command.comment(), "Approval comment is required");
        if (command.decision() == ApprovalDecision.APPROVE) {
            requireText(command.licenseNumber(), "License number is required when approving an application");
        }
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
