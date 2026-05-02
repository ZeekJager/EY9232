package com.trade.tradelicense.application.common;

import com.trade.tradelicense.domain.aggregates.TradeLicenseApplication;
import com.trade.tradelicense.domain.enums.ApplicationStatus;
import com.trade.tradelicense.domain.valueobjects.ApplicationId;

import java.util.List;
import java.util.Optional;

public interface TradeLicenseApplicationRepositoryPort {
    TradeLicenseApplication save(TradeLicenseApplication application);

    Optional<TradeLicenseApplication> findById(ApplicationId applicationId);

    List<TradeLicenseApplication> findByStatus(ApplicationStatus status);
}
