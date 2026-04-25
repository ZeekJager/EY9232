package com.trade.tradelicense.application.commands;

import com.trade.tradelicense.application.common.ReviewAction;
import lombok.Value;

import java.util.UUID;

/**
 * Immutable command carrying the reviewer's decision on a pending trade license
 * application.
 *
 * <p>Handled by
 * {@link com.trade.tradelicense.application.commands.handlers.ReviewTradeLicenseApplicationCommandHandler}.
 */
@Value
public class ReviewTradeLicenseApplicationCommand {

    /** UUID of the application being reviewed. */
    UUID applicationId;

    /** UUID of the {@link com.trade.tradelicense.domain.entities.User} performing the review (role: REVIEWER). */
    UUID reviewerId;

    /**
     * The review decision ({@link ReviewAction#ACCEPT}, {@link ReviewAction#REJECT},
     * or {@link ReviewAction#ADJUST}).
     */
    ReviewAction action;

    /** Optional reviewer comments (e.g. rejection reason, adjustment instructions). */
    String comments;
}
