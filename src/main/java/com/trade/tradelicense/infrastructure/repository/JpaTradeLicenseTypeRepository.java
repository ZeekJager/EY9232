package com.trade.tradelicense.infrastructure.repository;

import com.trade.tradelicense.domain.entities.TradeLicenseType;
import com.trade.tradelicense.domain.repository.ITradeLicenseTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * JPA-backed implementation of {@link ITradeLicenseTypeRepository}.
 *
 * <p>Delegates all persistence operations to the inner Spring Data JPA interface
 * {@link SpringDataTradeLicenseTypeRepository}.
 */
@Repository
@RequiredArgsConstructor
public class JpaTradeLicenseTypeRepository implements ITradeLicenseTypeRepository {

    private final SpringDataTradeLicenseTypeRepository springRepo;

    @Override
    public Optional<TradeLicenseType> findById(UUID id) {
        return springRepo.findById(id);
    }

    @Override
    public List<TradeLicenseType> findAll() {
        return springRepo.findAll();
    }

    /**
     * Inner Spring Data JPA interface for {@link TradeLicenseType} persistence.
     */
    interface SpringDataTradeLicenseTypeRepository extends JpaRepository<TradeLicenseType, UUID> {
        // Inherits findById and findAll from JpaRepository.
    }
}
