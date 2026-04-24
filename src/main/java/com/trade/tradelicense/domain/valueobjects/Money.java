package com.trade.tradelicense.domain.valueobjects;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;

/**
 * Value object representing a monetary amount together with its currency.
 *
 * <p>{@code Money} is an embeddable value object used by the
 * {@link com.trade.tradelicense.domain.entities.Payment} entity to express the
 * fee payable for a trade-license application.  Coupling the numeric amount
 * with its ISO 4217 currency code in a single value object prevents the
 * common mistake of operating on amounts from different currencies without
 * an explicit conversion.
 *
 * <p>Arithmetic operations (addition, subtraction, comparison) should be
 * performed only after verifying that both operands share the same
 * {@code currency}.
 */
@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class Money {

    /**
     * The monetary amount, stored with up to four decimal places of precision.
     */
    @Column(nullable = false, precision = 19, scale = 4)
    private final BigDecimal amount;

    /**
     * ISO 4217 three-letter currency code (e.g. {@code "AED"}, {@code "USD"}).
     */
    @Column(nullable = false, length = 3)
    private final String currency;
}
