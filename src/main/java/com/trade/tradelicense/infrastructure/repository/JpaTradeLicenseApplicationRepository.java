package com.trade.tradelicense.infrastructure.repository;

import com.trade.tradelicense.domain.ApplicationStatus;
import com.trade.tradelicense.domain.aggregate.TradeLicenseApplication;
import com.trade.tradelicense.domain.repository.ITradeLicenseApplicationRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

/**
 * JPA-backed implementation of {@link ITradeLicenseApplicationRepository}.
 *
 * <p>Extends both {@link JpaRepository} (for built-in CRUD operations) and the
 * domain interface, so this single Spring Data repository can be injected wherever
 * {@link ITradeLicenseApplicationRepository} is required.
 */
@Repository
public interface JpaTradeLicenseApplicationRepository
        extends JpaRepository<TradeLicenseApplication, UUID>, ITradeLicenseApplicationRepository {

    /**
     * {@inheritDoc}
     *
     * <p>Uses a JPQL query to traverse the {@code applicant} association.
     */
    @Override
    @Query("SELECT a FROM TradeLicenseApplication a WHERE a.applicant.id = :applicantId")
    List<TradeLicenseApplication> findByApplicantId(@Param("applicantId") UUID applicantId);

    /**
     * {@inheritDoc}
     *
     * <p>Derived from the {@code status} field name via Spring Data naming conventions.
     */
    @Override
    List<TradeLicenseApplication> findByStatus(ApplicationStatus status);
}
