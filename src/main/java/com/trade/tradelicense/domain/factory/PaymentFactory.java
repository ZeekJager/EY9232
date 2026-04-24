package com.trade.tradelicense.domain.factory;

import com.trade.tradelicense.domain.entities.Payment;
import com.trade.tradelicense.domain.valueobjects.PaymentStatus;
import com.trade.tradelicense.domain.valueobjects.Money;
import org.springframework.stereotype.Component;

/**
 * Factory for creating {@link Payment} instances associated with a
 * trade-license application.
 *
 * <p>A newly created payment is always in the
 * {@link PaymentStatus#INITIATED} state — meaning the record exists but no
 * funds have been received yet.  The fee amount is expressed as a {@link Money}
 * value object that carries both the monetary value and its ISO 4217 currency
 * code, preventing unit confusion.
 *
 * <p>The factory does <em>not</em> persist the payment; callers are
 * responsible for assigning it to the owning
 * {@link com.trade.tradelicense.domain.entities.TradeLicenseApplication} and
 * persisting through the appropriate repository.
 *
 * @see Payment
 * @see Money
 */
@Component
public class PaymentFactory {

    /**
     * Creates and returns a new {@link Payment} in the
     * {@link PaymentStatus#INITIATED} state.
     *
     * @param amount the fee amount (value + currency) to be settled;
     *               must not be {@code null}
     * @return a new, unsaved {@link Payment}
     */
    public Payment create(Money amount) {
        return Payment.builder()
                .amount(amount.getAmount())
                .currency(amount.getCurrency())
                .status(PaymentStatus.INITIATED)
                .build();
    }
}
