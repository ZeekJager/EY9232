package com.trade.tradelicense.domain.valueobjects;

/**
 * Lifecycle states of a {@link com.trade.tradelicense.domain.entities.Payment} associated with a
 * {@link com.trade.tradelicense.domain.aggregate.TradeLicenseApplication}.
 *
 * <p>A {@link com.trade.tradelicense.domain.aggregate.TradeLicenseApplication} may only be submitted when the linked
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
