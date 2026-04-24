package com.trade.tradelicense.infrastructure.repository;

import com.trade.tradelicense.domain.entities.License;
import com.trade.tradelicense.domain.repository.ITradeLicenseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

/**
 * JPA-backed implementation of {@link ITradeLicenseRepository}.
 *
 * <p>Delegates all persistence operations to the inner Spring Data JPA interface
 * {@link SpringDataLicenseRepository}.
 */
@Repository
@RequiredArgsConstructor
public class JpaTradeLicenseRepository implements ITradeLicenseRepository {

    private final SpringDataLicenseRepository springRepo;

    @Override
    public Optional<License> findById(UUID id) {
        return springRepo.findById(id);
    }

    @Override
    public Optional<License> findByApplicationId(UUID applicationId) {
        return springRepo.findByApplication_Id(applicationId);
    }

    @Override
    public License save(License license) {
        return springRepo.save(license);
    }

    /**
     * Inner Spring Data JPA interface for {@link License} persistence.
     */
    interface SpringDataLicenseRepository extends JpaRepository<License, UUID> {

        /**
         * Finds the license issued for a specific application.
         *
         * @param applicationId UUID of the originating application
         * @return the matching license, or empty if not yet issued
         */
        @Query("SELECT l FROM License l WHERE l.application.id = :applicationId")
        Optional<License> findByApplication_Id(@Param("applicationId") UUID applicationId);
    }
}
