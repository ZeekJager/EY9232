package com.trade.tradelicense.application.events;

import lombok.Value;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Immutable event published after an application has been approved and a
 * {@link com.trade.tradelicense.domain.entities.License} has been issued.
 *
 * <p>Listeners should notify the applicant that their license is ready.
 */
@Value
public class ApplicationApprovedEvent {

    /** UUID of the approved application. */
    UUID applicationId;

    /** UUID of the newly issued license. */
    UUID licenseId;

    /** UUID of the applicant who will receive the license. */
    UUID applicantId;

    /** Timestamp at which the approval occurred. */
    LocalDateTime occurredAt;
}
