package com.trade.tradelicense.infrastructure.repository;

import com.trade.tradelicense.domain.entities.License;
import com.trade.tradelicense.domain.repository.ITradeLicenseRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

/**
 * JPA-backed implementation of {@link ITradeLicenseRepository}.
 *
 * <p>Extends both {@link JpaRepository} and the domain interface so this single
 * Spring Data repository can be injected wherever {@link ITradeLicenseRepository}
 * is required.
 */
@Repository
public interface JpaTradeLicenseRepository
        extends JpaRepository<License, UUID>, ITradeLicenseRepository {

    /**
     * {@inheritDoc}
     *
     * <p>Uses a JPQL query to traverse the {@code application} association.
     */
    @Override
    @Query("SELECT l FROM License l WHERE l.application.id = :applicationId")
    Optional<License> findByApplicationId(@Param("applicationId") UUID applicationId);
}
