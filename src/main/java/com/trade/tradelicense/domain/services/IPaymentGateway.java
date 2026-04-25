package com.trade.tradelicense.domain.services;

import java.math.BigDecimal;

/**
 * Domain service interface for interacting with an external payment gateway.
 *
 * <p>Implementations must integrate with a real payment provider (e.g. Stripe,
 * PayFort) and must not expose provider-specific details into the domain or
 * application layers.
 */
public interface IPaymentGateway {

    /**
     * Verifies that the payment identified by the given reference has been
     * successfully settled.
     *
     * @param paymentReference the external payment reference to verify;
     *                         must not be {@code null}
     * @return {@code true} if the payment is confirmed as settled,
     *         {@code false} otherwise
     */
    boolean verifyPayment(String paymentReference);

    /**
     * Initiates a new payment for the specified amount and returns the
     * external payment reference that the client can use to complete the
     * transaction.
     *
     * @param amount   the monetary amount to charge; must not be {@code null}
     * @param currency the ISO 4217 currency code (e.g. {@code "AED"});
     *                 must not be {@code null}
     * @return the external payment reference string
     */
    String initiatePayment(BigDecimal amount, String currency);
}
