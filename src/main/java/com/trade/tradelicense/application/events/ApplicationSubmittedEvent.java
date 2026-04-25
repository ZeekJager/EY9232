package com.trade.tradelicense.application.events;

import lombok.Value;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Immutable event published after a trade license application has been
 * successfully submitted (transitioned from {@code DRAFT} to {@code PENDING}).
 *
 * <p>Listeners of this event are responsible for notifying reviewers that a
 * new application is awaiting review.
 */
@Value
public class ApplicationSubmittedEvent {

    /** UUID of the submitted application. */
    UUID applicationId;

    /** UUID of the applicant who submitted the application. */
    UUID applicantId;

    /** Display name of the applicant, for use in notifications. */
    String applicantName;

    /** Timestamp at which the submission occurred. */
    LocalDateTime occurredAt;
}
