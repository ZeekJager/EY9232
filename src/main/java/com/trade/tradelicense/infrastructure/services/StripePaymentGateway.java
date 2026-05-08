package com.trade.tradelicense.infrastructure.services;

import com.trade.tradelicense.domain.services.IPaymentGateway;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * Stub {@link IPaymentGateway} implementation simulating Stripe integration.
 *
 * <p>All methods log their invocation and return stub values. Replace with a
 * real Stripe SDK integration (e.g. {@code com.stripe.net.RequestOptions}) for
 * production use.
 *
 * <p>A real implementation would:
 * <ul>
 *   <li>Inject the Stripe API secret key from application properties.</li>
 *   <li>Call {@code PaymentIntent.retrieve(paymentReference)} to verify.</li>
 *   <li>Call {@code PaymentIntent.create(params)} to initiate a new payment.</li>
 * </ul>
 */
@Slf4j
@Service
public class StripePaymentGateway implements IPaymentGateway {

    /**
     * {@inheritDoc}
     *
     * <p>Stub: always returns {@code true} and logs the verification attempt.
     * A real implementation would call the Stripe API to check payment status.
     */
    @Override
    public boolean verifyPayment(String paymentReference) {
        log.info("[PAYMENT] Verifying payment reference '{}'", paymentReference);
        // TODO: integrate with Stripe PaymentIntent.retrieve(paymentReference)
        return true;
    }

    /**
     * {@inheritDoc}
     *
     * <p>Stub: returns a generated fake reference and logs the initiation.
     * A real implementation would call the Stripe API to create a PaymentIntent.
     */
    @Override
    public String initiatePayment(BigDecimal amount, String currency) {
        String reference = "pi_stub_" + UUID.randomUUID().toString().replace("-", "");
        log.info("[PAYMENT] Initiated payment of {} {} — reference: {}", amount, currency, reference);
        // TODO: integrate with Stripe PaymentIntent.create(params)
        return reference;
    }
}
