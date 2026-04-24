package com.trade.tradelicense.domain.repository;

import com.trade.tradelicense.domain.entities.License;

import java.util.Optional;
import java.util.UUID;

/**
 * Domain repository interface for the {@link License} entity.
 *
 * <p>Defines the persistence contract from the domain's perspective. Only the
 * queries relevant to the domain model are exposed here.
 */
public interface ITradeLicenseRepository {

    /**
     * Finds a license by its unique identifier.
     *
     * @param id the license UUID; must not be {@code null}
     * @return an {@link Optional} containing the license, or empty if not found
     */
    Optional<License> findById(UUID id);

    /**
     * Finds the license that was issued for the given application.
     *
     * @param applicationId the UUID of the originating
     *                      {@link com.trade.tradelicense.domain.aggregate.TradeLicenseApplication}
     * @return an {@link Optional} containing the license, or empty if not issued yet
     */
    Optional<License> findByApplicationId(UUID applicationId);

    /**
     * Persists or updates a license entity.
     *
     * @param license the license to save; must not be {@code null}
     * @return the saved license
     */
    License save(License license);
}
