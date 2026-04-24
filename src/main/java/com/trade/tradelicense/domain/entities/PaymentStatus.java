package com.trade.tradelicense.domain.entities;

/**
 * Lifecycle states of a {@link Payment} associated with a
 * {@link TradeLicenseApplication}.
 *
 * <p>A {@link TradeLicenseApplication} may only be submitted when the linked
 * payment has reached the {@link #SETTLED} state.
 */
public enum PaymentStatus {

    /** Payment record has been created but not yet completed. */
    INITIATED,

    /**
     * Funds have been successfully received and confirmed.
     * This is the required state before an application can be submitted.
     */
    SETTLED,

    /** Payment processing failed; a new payment attempt may be needed. */
    FAILED
}
