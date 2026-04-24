package com.trade.tradelicense.domain.valueobjects;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;

/**
 * Value object representing a monetary fee combining an amount and its ISO 4217
 * currency code.
 *
 * <p>{@code LicenseFee} is the domain representation of the fee that an applicant
 * must pay when obtaining a trade license. By coupling the numeric amount with
 * its currency in a single value object, the domain model makes it impossible to
 * accidentally add or compare fees in different currencies without an explicit
 * conversion — for example, adding {@code 100 USD} to {@code 100 AED} will
 * throw an {@link IllegalArgumentException} from the {@link #add(LicenseFee)}
 * helper rather than silently producing a wrong result.
 *
 * <p>Two {@code LicenseFee} instances are equal when both the amount (using
 * {@link BigDecimal#compareTo} semantics) and the currency code are identical.
 *
 * @see Money
 */
@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
@EqualsAndHashCode
@ToString
public class LicenseFee {

    /** Regex matching exactly three uppercase ASCII letters (ISO 4217). */
    private static final String ISO_4217_PATTERN = "^[A-Z]{3}$";

    /**
     * The fee amount; must be positive and stored with up to four decimal
     * places of precision.
     */
    @NotNull
    @Positive
    @Column(name = "fee_amount", nullable = false, precision = 19, scale = 4)
    private final BigDecimal amount;

    /**
     * ISO 4217 three-letter currency code (e.g. {@code "AED"}, {@code "USD"}).
     */
    @NotNull
    @Pattern(regexp = ISO_4217_PATTERN,
             message = "Currency must be a valid ISO 4217 three-letter uppercase code (e.g. 'AED', 'USD')")
    @Column(name = "fee_currency", nullable = false, length = 3)
    private final String currency;

    /**
     * Constructs a {@code LicenseFee} after validating the amount and currency.
     *
     * @param amount   the fee amount; must not be {@code null} and must be positive
     * @param currency the ISO 4217 currency code; must be exactly three uppercase letters
     * @throws IllegalArgumentException if any argument fails validation
     */
    public LicenseFee(BigDecimal amount, String currency) {
        if (amount == null) {
            throw new IllegalArgumentException("amount must not be null");
        }
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("amount must be positive, but was: " + amount);
        }
        if (currency == null || !currency.matches(ISO_4217_PATTERN)) {
            throw new IllegalArgumentException(
                    "currency must be a valid ISO 4217 three-letter uppercase code (e.g. 'AED'), but was: " + currency);
        }
        this.amount = amount;
        this.currency = currency;
    }

    /**
     * Returns the sum of this fee and the given {@code other} fee.
     *
     * @param other the fee to add; must not be {@code null} and must share the
     *              same currency as this fee
     * @return a new {@code LicenseFee} whose amount is the sum of both amounts
     * @throws IllegalArgumentException if the currencies differ
     */
    public LicenseFee add(LicenseFee other) {
        if (other == null) {
            throw new IllegalArgumentException("other must not be null");
        }
        if (!this.currency.equals(other.currency)) {
            throw new IllegalArgumentException(
                    "Cannot add fees with different currencies: " + this.currency + " vs " + other.currency);
        }
        return new LicenseFee(this.amount.add(other.amount), this.currency);
    }
}
