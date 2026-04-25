package com.trade.tradelicense.application.commands;

import lombok.Value;

import java.util.List;
import java.util.UUID;

/**
 * Immutable command that triggers the submission of a new trade license
 * application.
 *
 * <p>Carries all information required by the
 * {@link com.trade.tradelicense.application.commands.handlers.SubmitTradeLicenseApplicationCommandHandler}
 * to create a new application and transition it from {@code DRAFT} to
 * {@code PENDING}.
 */
@Value
public class SubmitTradeLicenseApplicationCommand {

    /** UUID of the applicant {@link com.trade.tradelicense.domain.entities.User}. */
    UUID applicantId;

    /** UUID of the selected {@link com.trade.tradelicense.domain.entities.TradeLicenseType}. */
    UUID tradeLicenseTypeId;

    /** UUIDs of attachments already uploaded for this application. */
    List<UUID> attachmentIds;

    /** External payment reference to be verified via the payment gateway. */
    String paymentReference;
}
