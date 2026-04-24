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
 * Value object representing a physical business address.
 *
 * <p>Grouping the four address components — {@link #street}, {@link #city},
 * {@link #postalCode}, and {@link #country} — into a single {@code BusinessAddress}
 * value object ensures that addresses are always complete and consistently
 * structured across the domain model.  It also provides a natural home for
 * address-level validation rules (e.g. postal-code format checks) that would
 * otherwise scatter across multiple entity classes.
 *
 * <p>The {@code country} field must be a valid
 * <a href="https://www.iso.org/iso-3166-country-codes.html">ISO 3166-1 alpha-2</a>
 * two-letter country code (e.g. {@code "AE"} for the UAE, {@code "US"} for the
 * United States).  Postal code format validation is intentionally left lenient
 * at the domain level because formats vary enormously by country; stricter
 * jurisdiction-specific rules can be applied in the application layer before
 * constructing this object.
 *
 * <p>Two {@code BusinessAddress} instances are equal when all four fields are
 * identical (case-sensitive comparison).
 */
@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
@EqualsAndHashCode
@ToString
public class BusinessAddress {

    /** ISO 3166-1 alpha-2 pattern: exactly two uppercase ASCII letters. */
    private static final String ISO_3166_ALPHA2_PATTERN = "^[A-Z]{2}$";

    /** Postal code pattern: 3–10 alphanumeric characters (covers most formats). */
    private static final String POSTAL_CODE_PATTERN = "^[A-Z0-9 \\-]{3,10}$";

    /**
     * Street address including building number and street name
     * (e.g. {@code "123 Sheikh Zayed Road"}).
     */
    @NotBlank
    @Size(max = 255)
    @Column(name = "address_street", nullable = false, length = 255)
    private final String street;

    /**
     * City or locality name (e.g. {@code "Dubai"}).
     */
    @NotBlank
    @Size(max = 100)
    @Column(name = "address_city", nullable = false, length = 100)
    private final String city;

    /**
     * Postal or ZIP code for this address (e.g. {@code "12345"},
     * {@code "SW1A 1AA"}).  Validated to be 3–10 uppercase alphanumeric
     * characters (hyphens and spaces allowed).
     */
    @NotBlank
    @Pattern(regexp = POSTAL_CODE_PATTERN,
             message = "Postal code must be 3–10 uppercase alphanumeric characters (hyphens and spaces allowed)")
    @Column(name = "address_postal_code", nullable = false, length = 10)
    private final String postalCode;

    /**
     * ISO 3166-1 alpha-2 two-letter country code (e.g. {@code "AE"}, {@code "US"}).
     */
    @NotBlank
    @Pattern(regexp = ISO_3166_ALPHA2_PATTERN,
             message = "Country must be a valid ISO 3166-1 alpha-2 two-letter uppercase code (e.g. 'AE', 'US')")
    @Column(name = "address_country", nullable = false, length = 2)
    private final String country;

    /**
     * Constructs a {@code BusinessAddress} and validates all fields.
     *
     * @param street     street address including building number; must not be blank
     * @param city       city name; must not be blank
     * @param postalCode postal/ZIP code; must be 3–10 uppercase alphanumeric characters
     * @param country    ISO 3166-1 alpha-2 country code; must be exactly two uppercase letters
     * @throws IllegalArgumentException if any argument fails validation
     */
    public BusinessAddress(String street, String city, String postalCode, String country) {
        if (street == null || street.isBlank()) {
            throw new IllegalArgumentException("street must not be null or blank");
        }
        if (city == null || city.isBlank()) {
            throw new IllegalArgumentException("city must not be null or blank");
        }
        if (postalCode == null || postalCode.isBlank()) {
            throw new IllegalArgumentException("postalCode must not be null or blank");
        }
        String normalizedPostal = postalCode.trim().toUpperCase();
        if (!normalizedPostal.matches(POSTAL_CODE_PATTERN)) {
            throw new IllegalArgumentException(
                    "Invalid postalCode '" + postalCode
                    + "'. Must be 3–10 uppercase alphanumeric characters (hyphens and spaces allowed).");
        }
        if (country == null || country.isBlank()) {
            throw new IllegalArgumentException("country must not be null or blank");
        }
        String normalizedCountry = country.trim().toUpperCase();
        if (!normalizedCountry.matches(ISO_3166_ALPHA2_PATTERN)) {
            throw new IllegalArgumentException(
                    "Invalid country code '" + country
                    + "'. Must be an ISO 3166-1 alpha-2 two-letter uppercase code (e.g. 'AE', 'US').");
        }
        this.street = street.trim();
        this.city = city.trim();
        this.postalCode = normalizedPostal;
        this.country = normalizedCountry;
    }
}
