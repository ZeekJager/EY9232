package com.trade.tradelicense.domain.valueobjects;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Value object representing a Tax Identification Number (TIN) issued by a
 * government authority.
 *
 * <p>A {@code TaxIdentificationNumber} wraps the raw TIN string and validates
 * it at construction time.  The validation enforces the following rules that
 * are common across jurisdictions:
 * <ul>
 *   <li>Must consist solely of alphanumeric characters (letters and digits).</li>
 *   <li>Must be between 5 and 20 characters long.</li>
 * </ul>
 * Applications that need stricter jurisdiction-specific rules (e.g. a
 * 15-digit UAE TRN, or a US EIN with exactly nine digits) should validate
 * against that schema before constructing this object.
 *
 * <p>Two {@code TaxIdentificationNumber} instances are equal if and only if
 * their normalized (uppercase) string values are identical.
 */
@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
@EqualsAndHashCode
@ToString
public class TaxIdentificationNumber {

    /** Regex: 5–20 uppercase alphanumeric characters. */
    private static final String TIN_PATTERN = "^[A-Z0-9]{5,20}$";

    /**
     * The normalized (uppercase) TIN string.
     */
    @NotBlank
    @Size(min = 5, max = 20)
    @Pattern(regexp = TIN_PATTERN,
             message = "TIN must consist of 5–20 uppercase alphanumeric characters")
    @Column(name = "tin_value", nullable = false, length = 20)
    private final String value;

    /**
     * Constructs a {@code TaxIdentificationNumber} from the given raw string.
     *
     * <p>The value is normalized to uppercase before validation so that
     * lower-case input is accepted transparently.
     *
     * @param value the raw TIN string; must not be blank and must match the
     *              alphanumeric format (5–20 characters)
     * @throws IllegalArgumentException if the value is null, blank, or does not
     *                                  meet the format requirements
     */
    public TaxIdentificationNumber(String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("TIN must not be null or blank");
        }
        String normalized = value.trim().toUpperCase();
        if (!normalized.matches(TIN_PATTERN)) {
            throw new IllegalArgumentException(
                    "Invalid TIN '" + value + "'. Must be 5–20 uppercase alphanumeric characters.");
        }
        this.value = normalized;
    }
}
