package com.trade.tradelicense.domain.valueobjects;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Value object encapsulating an international phone number.
 *
 * <p>A {@code PhoneNumber} is stored as a normalized E.164-style string that
 * includes the country dial code (e.g. {@code "+971501234567"} for a UAE
 * mobile, or {@code "+12025551234"} for a US number).  The value is validated
 * at construction time to ensure it begins with a {@code '+'} followed by
 * one-to-three digit country code and then six-to-fourteen subscriber digits,
 * giving a total length of 8–18 characters (inclusive of the leading
 * {@code '+'}).
 *
 * <p>Two {@code PhoneNumber} instances are equal if and only if their
 * normalised string values are identical.
 */
@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
@EqualsAndHashCode
@ToString
public class PhoneNumber {

    /**
     * Regex for a valid E.164 phone number:
     * <ul>
     *   <li>{@code +} — mandatory leading plus sign</li>
     *   <li>{@code [1-9]\d{0,2}} — country code (1–3 digits, no leading zero)</li>
     *   <li>{@code \d{6,14}} — subscriber number (6–14 digits)</li>
     * </ul>
     * Total length including {@code +}: 8–18 characters.
     */
    private static final String E164_PATTERN = "^\\+[1-9]\\d{0,2}\\d{6,14}$";

    /**
     * The normalized phone number string in E.164 format
     * (e.g. {@code "+971501234567"}).
     */
    @NotBlank
    @Pattern(regexp = E164_PATTERN,
             message = "Phone number must be in E.164 format: '+' followed by country code (1-3 digits) and subscriber number (6-14 digits)")
    @Column(nullable = false, length = 20)
    private final String value;

    /**
     * Constructs a {@code PhoneNumber} from the given string, validating that
     * it conforms to E.164 format.
     *
     * @param value the phone number string (e.g. {@code "+971501234567"});
     *              must not be {@code null} or blank
     * @throws IllegalArgumentException if the value is null, blank, or does
     *                                  not match the E.164 pattern
     */
    public PhoneNumber(String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Phone number must not be null or blank");
        }
        if (!value.matches(E164_PATTERN)) {
            throw new IllegalArgumentException(
                    "Invalid phone number '" + value + "'. Must be in E.164 format: "
                    + "'+' followed by 1-3 digit country code and 6-14 subscriber digits.");
        }
        this.value = value;
    }
}
