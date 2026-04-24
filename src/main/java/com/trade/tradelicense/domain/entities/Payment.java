package com.trade.tradelicense.domain.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Represents the payment transaction associated with a
 * {@link TradeLicenseApplication}.
 *
 * <p>An application may only be submitted (via
 * {@link WorkflowAction#SUBMIT}) when its linked {@code Payment} has reached
 * the {@link PaymentStatus#SETTLED} state. This invariant is enforced by the
 * {@link TradeLicenseApplication} aggregate root.
 */
@Entity
@Table(name = "payments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Payment {

    /**
     * Surrogate primary key identifying this payment record.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The monetary amount due for this trade-license application, expressed in
     * the system's base currency.
     */
    @Column(nullable = false, precision = 19, scale = 4)
    private BigDecimal amount;

    /**
     * Current lifecycle state of the payment.
     * The {@link TradeLicenseApplication} aggregate checks that this is
     * {@link PaymentStatus#SETTLED} before allowing submission.
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentStatus status;

    /**
     * Timestamp at which the payment was confirmed as settled, or {@code null}
     * if settlement has not yet occurred.
     */
    @Column
    private LocalDateTime settledAt;
}
