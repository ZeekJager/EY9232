package com.trade.tradelicense.application.validators;

import com.trade.tradelicense.application.commands.SubmitTradeLicenseApplicationCommand;
import com.trade.tradelicense.application.exceptions.InvalidWorkflowActionException;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * Validates the preconditions required to submit a trade license application.
 *
 * <p>This validator operates on the incoming command rather than the domain
 * aggregate, checking that all required fields are present before the
 * application is created and transitioned to {@code PENDING}.
 */
@Component
public class SubmitApplicationValidator {

    /**
     * Validates the given submit command.
     *
     * @param command the command to validate; must not be {@code null}
     * @throws InvalidWorkflowActionException if the command is missing required
     *                                        data (no attachments or no payment reference)
     */
    public void validate(SubmitTradeLicenseApplicationCommand command) {
        if (command.getAttachmentIds() == null || command.getAttachmentIds().isEmpty()) {
            throw new InvalidWorkflowActionException(
                    "At least one attachment must be provided before submitting an application.");
        }

        if (!StringUtils.hasText(command.getPaymentReference())) {
            throw new InvalidWorkflowActionException(
                    "A valid payment reference must be provided before submitting an application.");
        }
    }
}
