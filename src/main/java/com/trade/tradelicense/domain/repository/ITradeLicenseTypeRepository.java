package com.trade.tradelicense.domain.repository;

import com.trade.tradelicense.domain.entities.TradeLicenseType;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Domain repository interface for {@link TradeLicenseType} catalog entities.
 */
public interface ITradeLicenseTypeRepository {

    /**
     * Finds a trade license type by its unique identifier.
     *
     * @param id the UUID of the license type; must not be {@code null}
     * @return an {@link Optional} containing the license type, or empty if not found
     */
    Optional<TradeLicenseType> findById(UUID id);

    /**
     * Returns all available trade license types from the catalog.
     *
     * @return a (possibly empty) list of all license types
     */
    List<TradeLicenseType> findAll();
}
