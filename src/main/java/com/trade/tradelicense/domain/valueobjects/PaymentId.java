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
 * {@link com.trade.tradelicense.domain.entities.Payment}.
 *
 * <p>Type-safe wrapper around the raw {@code UUID} surrogate key to avoid
 * inadvertent mixing of different entity IDs in domain operations.
 */
@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class PaymentId {

    /** The underlying surrogate key value. */
    private final UUID value;
}
