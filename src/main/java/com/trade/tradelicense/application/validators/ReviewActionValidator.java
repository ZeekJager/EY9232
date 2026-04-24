package com.trade.tradelicense.application.validators;

import com.trade.tradelicense.application.exceptions.InvalidWorkflowActionException;
import com.trade.tradelicense.domain.ApplicationStatus;
import com.trade.tradelicense.domain.aggregate.TradeLicenseApplication;
import com.trade.tradelicense.domain.entities.User;
import com.trade.tradelicense.domain.valueobjects.UserRole;
import org.springframework.stereotype.Component;

/**
 * Validates the preconditions for a reviewer to act on a pending trade license
 * application.
 *
 * <p>Rules enforced:
 * <ol>
 *   <li>The application must be in {@link ApplicationStatus#PENDING} state.</li>
 *   <li>The acting user must hold the {@link UserRole#REVIEWER} role.</li>
 *   <li>The application must have at least one attachment on record.</li>
 * </ol>
 */
@Component
public class ReviewActionValidator {

    /**
     * Validates that the given reviewer may act on the given application.
     *
     * @param application the application to be reviewed; must not be {@code null}
     * @param reviewer    the user attempting the review action; must not be {@code null}
     * @throws InvalidWorkflowActionException if any precondition is violated
     */
    public void validate(TradeLicenseApplication application, User reviewer) {
        if (application.getStatus() != ApplicationStatus.PENDING) {
            throw InvalidWorkflowActionException.statusMismatch("REVIEW", application.getStatus());
        }

        if (reviewer.getRole() != UserRole.REVIEWER) {
            throw new InvalidWorkflowActionException(
                    "User '" + reviewer.getId() + "' does not have the REVIEWER role.");
        }

        if (application.getAttachments() == null || application.getAttachments().isEmpty()) {
            throw new InvalidWorkflowActionException(
                    "Application '" + application.getId() + "' has no attachments to review.");
        }
    }
}
