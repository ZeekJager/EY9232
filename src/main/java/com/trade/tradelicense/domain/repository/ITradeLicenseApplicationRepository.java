package com.trade.tradelicense.domain.repository;

import com.trade.tradelicense.domain.ApplicationStatus;
import com.trade.tradelicense.domain.aggregate.TradeLicenseApplication;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Domain repository interface for {@link TradeLicenseApplication} aggregate roots.
 *
 * <p>Defines the persistence contract from the domain's perspective. Infrastructure
 * implementations must not leak persistence concerns into the domain.
 */
public interface ITradeLicenseApplicationRepository {

    /**
     * Finds a trade license application by its unique identifier.
     *
     * @param id the application's UUID; must not be {@code null}
     * @return an {@link Optional} containing the application, or empty if not found
     */
    Optional<TradeLicenseApplication> findById(UUID id);

    /**
     * Returns all applications belonging to a given applicant.
     *
     * @param applicantId the UUID of the applicant {@link com.trade.tradelicense.domain.entities.User}
     * @return a (possibly empty) list of matching applications
     */
    List<TradeLicenseApplication> findByApplicantId(UUID applicantId);

    /**
     * Returns all applications currently in the given status.
     *
     * @param status the {@link ApplicationStatus} to filter by
     * @return a (possibly empty) list of matching applications
     */
    List<TradeLicenseApplication> findByStatus(ApplicationStatus status);

    /**
     * Persists or updates a trade license application.
     *
     * @param application the aggregate root to save; must not be {@code null}
     * @return the saved application (may differ from the input if IDs are generated)
     */
    TradeLicenseApplication save(TradeLicenseApplication application);

    /**
     * Deletes the application identified by the given UUID.
     *
     * @param id the UUID of the application to delete; must not be {@code null}
     */
    void deleteById(UUID id);
}
