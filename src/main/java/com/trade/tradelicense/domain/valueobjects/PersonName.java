package com.trade.tradelicense.domain.valueobjects;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Value object representing the full name of a person.
 *
 * <p>A {@code PersonName} is composed of a mandatory {@link #firstName} and
 * {@link #lastName} together with an optional {@link #middleName}.  Using a
 * dedicated value object (rather than a single {@code String name} field) makes
 * it possible to format, sort, or compare names consistently across the
 * domain — for example, generating formal salutations or populating legal
 * documents with the correct field ordering.
 *
 * <p>Two {@code PersonName} instances are equal when all three components are
 * identical (null-safe comparison for {@code middleName}).
 *
 * @see com.trade.tradelicense.domain.entities.User
 */
@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
@EqualsAndHashCode
@ToString
public class PersonName {

    /** Maximum allowed length for any name component. */
    private static final int MAX_COMPONENT_LENGTH = 100;

    /**
     * Given (first) name — mandatory.
     */
    @NotBlank
    @Size(max = MAX_COMPONENT_LENGTH)
    @Column(name = "first_name", nullable = false, length = MAX_COMPONENT_LENGTH)
    private final String firstName;

    /**
     * Middle name or initial — optional; may be {@code null}.
     */
    @Size(max = MAX_COMPONENT_LENGTH)
    @Column(name = "middle_name", length = MAX_COMPONENT_LENGTH)
    private final String middleName;

    /**
     * Family (last) name — mandatory.
     */
    @NotBlank
    @Size(max = MAX_COMPONENT_LENGTH)
    @Column(name = "last_name", nullable = false, length = MAX_COMPONENT_LENGTH)
    private final String lastName;

    /**
     * Constructs a {@code PersonName} without a middle name.
     *
     * @param firstName given name; must not be blank
     * @param lastName  family name; must not be blank
     */
    public PersonName(String firstName, String lastName) {
        this(firstName, null, lastName);
    }

    /**
     * Constructs a {@code PersonName} with all three components.
     *
     * @param firstName  given name; must not be blank
     * @param middleName middle name or initial; may be {@code null}
     * @param lastName   family name; must not be blank
     * @throws IllegalArgumentException if {@code firstName} or {@code lastName}
     *                                  is null or blank
     */
    public PersonName(String firstName, String middleName, String lastName) {
        if (firstName == null || firstName.isBlank()) {
            throw new IllegalArgumentException("First name must not be null or blank");
        }
        if (lastName == null || lastName.isBlank()) {
            throw new IllegalArgumentException("Last name must not be null or blank");
        }
        this.firstName = firstName.trim();
        this.middleName = (middleName != null && !middleName.isBlank()) ? middleName.trim() : null;
        this.lastName = lastName.trim();
    }

    /**
     * Returns the full name as a single string in the format
     * {@code "FirstName [MiddleName] LastName"}, omitting the middle name
     * component when it is absent.
     *
     * @return formatted full name
     */
    public String getFullName() {
        if (middleName != null && !middleName.isBlank()) {
            return firstName + " " + middleName + " " + lastName;
        }
        return firstName + " " + lastName;
    }
}
