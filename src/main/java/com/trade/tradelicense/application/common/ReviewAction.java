package com.trade.tradelicense.application.common;

/**
 * Actions available to a {@link com.trade.tradelicense.domain.valueobjects.UserRole#REVIEWER} when processing
 * a {@link com.trade.tradelicense.domain.aggregate.TradeLicenseApplication} in the
 * {@link com.trade.tradelicense.domain.ApplicationStatus#PENDING} state.
 */
public enum ReviewAction {

    /**
     * Accept the application and forward it to the approver queue.
     * Transitions status: {@code PENDING → REVIEWED}.
     */
    ACCEPT,

    /**
     * Reject the application outright.
     * Transitions status: {@code PENDING → REJECTED}.
     */
    REJECT,

    /**
     * Request adjustments from the applicant before proceeding.
     * Transitions status: {@code PENDING → DRAFT}.
     */
    ADJUST
}
