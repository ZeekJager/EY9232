package com.trade.tradelicense.application.commands;

import com.trade.tradelicense.application.common.ApprovalAction;
import lombok.Value;

import java.util.UUID;

/**
 * Immutable command carrying the approver's decision on a reviewed trade license
 * application.
 *
 * <p>Handled by
 * {@link com.trade.tradelicense.application.commands.handlers.ApproveTradeLicenseApplicationCommandHandler}.
 */
@Value
public class ApproveTradeLicenseApplicationCommand {

    /** UUID of the application under approval. */
    UUID applicationId;

    /** UUID of the {@link com.trade.tradelicense.domain.entities.User} performing the approval (role: APPROVER). */
    UUID approverId;

    /**
     * The approval decision ({@link ApprovalAction#APPROVE}, {@link ApprovalAction#REJECT},
     * or {@link ApprovalAction#REREVIEW}).
     */
    ApprovalAction action;

    /** Optional approver comments (e.g. rejection rationale). */
    String comments;
}
