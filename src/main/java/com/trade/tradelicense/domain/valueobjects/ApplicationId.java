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
 * Value object representing the stable identity of a
 * {@link com.trade.tradelicense.domain.entities.TradeLicenseApplication}.
 *
 * <p>Wrapping the raw {@code UUID} surrogate key in a typed value object prevents
 * accidental mix-ups between different entity IDs (e.g. passing an
 * {@link AttachmentId} where an {@code ApplicationId} is expected) and makes the
 * domain API self-documenting.
 */
@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class ApplicationId {

    /** The underlying surrogate key value. */
    private final UUID value;
}
