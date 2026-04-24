package com.trade.tradelicense.application.events;

import com.trade.tradelicense.application.common.ReviewAction;
import lombok.Value;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Immutable event published after a reviewer has acted on an application.
 *
 * <ul>
 *   <li>{@link ReviewAction#ACCEPT} — downstream listener should notify approvers.</li>
 *   <li>{@link ReviewAction#ADJUST} — downstream listener should notify the applicant
 *       to provide adjustments.</li>
 *   <li>{@link ReviewAction#REJECT} — downstream listener should notify the applicant
 *       of the rejection.</li>
 * </ul>
 */
@Value
public class ApplicationReviewedEvent {

    /** UUID of the reviewed application. */
    UUID applicationId;

    /** The review decision taken by the reviewer. */
    ReviewAction action;

    /** UUID of the reviewer who acted. */
    UUID reviewerId;

    /** Optional reviewer comments. */
    String comments;

    /** Timestamp at which the review action occurred. */
    LocalDateTime occurredAt;
}
