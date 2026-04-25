package com.trade.tradelicense.application.common;

/**
 * Actions available to a {@link com.trade.tradelicense.domain.valueobjects.UserRole#APPROVER} when processing
 * a {@link com.trade.tradelicense.domain.aggregate.TradeLicenseApplication} in the
 * {@link com.trade.tradelicense.domain.ApplicationStatus#REVIEWED} state.
 */
public enum ApprovalAction {

    /**
     * Grant the trade license.
     * Transitions status: {@code REVIEWED → APPROVED}.
     */
    APPROVE,

    /**
     * Reject the application.
     * Transitions status: {@code REVIEWED → REJECTED}.
     */
    REJECT,

    /**
     * Send the application back for re-review.
     * Transitions status: {@code REVIEWED → PENDING}.
     */
    REREVIEW
}
