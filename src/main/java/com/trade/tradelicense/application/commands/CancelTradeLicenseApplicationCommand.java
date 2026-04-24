package com.trade.tradelicense.application.commands;

import lombok.Value;

import java.util.UUID;

/**
 * Immutable command to cancel a trade license application.
 *
 * <p>Cancellation is only allowed when the application is in the
 * {@link com.trade.tradelicense.domain.ApplicationStatus#DRAFT} or
 * {@link com.trade.tradelicense.domain.ApplicationStatus#PENDING} state, and the
 * requesting user must be the application's owner.
 *
 * <p>Handled by
 * {@link com.trade.tradelicense.application.commands.handlers.CancelTradeLicenseApplicationCommandHandler}.
 */
@Value
public class CancelTradeLicenseApplicationCommand {

    /** UUID of the application to cancel. */
    UUID applicationId;

    /** UUID of the applicant requesting cancellation. */
    UUID applicantId;
}
