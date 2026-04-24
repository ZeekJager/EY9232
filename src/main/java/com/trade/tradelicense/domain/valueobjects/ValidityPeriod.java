package com.trade.tradelicense.domain.valueobjects;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;

/**
 * Value object representing a contiguous date range with an inclusive start and
 * exclusive-or-inclusive end boundary.
 *
 * <p>A {@code ValidityPeriod} is used wherever the domain requires a bounded
 * time window — most notably as the validity window printed on an issued trade
 * license (e.g. 1 January 2025 – 31 December 2025).  The value object
 * guarantees at construction time that the end date is strictly <em>after</em>
 * the start date, preventing degenerate or reversed periods from ever entering
 * the domain model.
 *
 * <p>Two {@code ValidityPeriod} instances are equal if and only if both
 * {@code startDate} and {@code endDate} are equal.
 */
@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
@EqualsAndHashCode
@ToString
public class ValidityPeriod {

    /**
     * The first day on which the license is valid (inclusive).
     */
    @NotNull
    @Column(name = "validity_start_date", nullable = false)
    private final LocalDate startDate;

    /**
     * The last day on which the license is valid (inclusive).
     * Must be strictly after {@link #startDate}.
     */
    @NotNull
    @Column(name = "validity_end_date", nullable = false)
    private final LocalDate endDate;

    /**
     * Constructs a {@code ValidityPeriod} and validates that the end date is
     * strictly after the start date.
     *
     * @param startDate the first day of the validity window; must not be {@code null}
     * @param endDate   the last day of the validity window; must not be {@code null}
     *                  and must be strictly after {@code startDate}
     * @throws IllegalArgumentException if either argument is {@code null} or if
     *                                  {@code endDate} is not after {@code startDate}
     */
    public ValidityPeriod(LocalDate startDate, LocalDate endDate) {
        if (startDate == null) {
            throw new IllegalArgumentException("startDate must not be null");
        }
        if (endDate == null) {
            throw new IllegalArgumentException("endDate must not be null");
        }
        if (!endDate.isAfter(startDate)) {
            throw new IllegalArgumentException(
                    "endDate (" + endDate + ") must be strictly after startDate (" + startDate + ")");
        }
        this.startDate = startDate;
        this.endDate = endDate;
    }

    /**
     * Returns {@code true} if the given date falls within this validity period
     * (inclusive on both ends).
     *
     * @param date the date to test; must not be {@code null}
     * @return {@code true} if {@code startDate <= date <= endDate}
     */
    public boolean contains(LocalDate date) {
        if (date == null) {
            throw new IllegalArgumentException("date must not be null");
        }
        return !date.isBefore(startDate) && !date.isAfter(endDate);
    }

    /**
     * Returns {@code true} if this validity period is currently active as of
     * today's date.
     *
     * @return {@code true} if today is within the validity window
     */
    public boolean isCurrentlyValid() {
        return contains(LocalDate.now());
    }
}
