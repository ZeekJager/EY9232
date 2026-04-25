package com.trade.tradelicense.infrastructure.repository;

import com.trade.tradelicense.domain.entities.TradeLicenseType;
import com.trade.tradelicense.domain.repository.ITradeLicenseTypeRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * JPA-backed implementation of {@link ITradeLicenseTypeRepository}.
 *
 * <p>Extends both {@link JpaRepository} and the domain interface so this single
 * Spring Data repository can be injected wherever {@link ITradeLicenseTypeRepository}
 * is required.
 */
@Repository
public interface JpaTradeLicenseTypeRepository
        extends JpaRepository<TradeLicenseType, UUID>, ITradeLicenseTypeRepository {
    // findById and findAll are inherited from JpaRepository.
}
