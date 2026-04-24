package com.trade.tradelicense.domain.valueobjects;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.UUID;

/**
 * Value object representing the identity of a
 * {@link com.trade.tradelicense.domain.entities.TradeLicenseType} catalog entry.
 *
 * <p>Type-safe wrapper around the raw {@code UUID} surrogate key used when
 * referencing a specific trade license type across aggregate boundaries.
 */
@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class TradeLicenseTypeId {

    /** The underlying surrogate key value. */
    private final UUID value;
}
