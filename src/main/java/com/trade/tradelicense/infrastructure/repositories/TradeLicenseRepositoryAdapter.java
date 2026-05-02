package com.trade.tradelicense.infrastructure.repositories;

import com.trade.tradelicense.domain.aggregates.TradeLicense;
import com.trade.tradelicense.domain.entities.User;
import com.trade.tradelicense.application.common.TradeLicenseRepositoryPort;
import org.springframework.stereotype.Repository;
import com.trade.tradelicense.infrastructure.persistence.JpaTradeLicenseEntity;

@Repository
public class TradeLicenseRepositoryAdapter implements TradeLicenseRepositoryPort {
    private final SpringDataTradeLicenseRepository repository;

    public TradeLicenseRepositoryAdapter(SpringDataTradeLicenseRepository repository) {
        this.repository = repository;
    }

    @Override
    public TradeLicense save(TradeLicense tradeLicense) {
        repository.save(toEntity(tradeLicense));
        return tradeLicense;
    }

    private JpaTradeLicenseEntity toEntity(TradeLicense tradeLicense) {
        User licenseHolder = tradeLicense.licenseHolder();
        return new JpaTradeLicenseEntity(
                tradeLicense.id().value(),
                tradeLicense.licenseNumber().value(),
                tradeLicense.sourceApplicationId().value(),
                licenseHolder.getUserId().value(),
                licenseHolder.getFullName().value(),
                tradeLicense.licenseType().code(),
                tradeLicense.commodity().code(),
                tradeLicense.issuedDate()
        );
    }
}
