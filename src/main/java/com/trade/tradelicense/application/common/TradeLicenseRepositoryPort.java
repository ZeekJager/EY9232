package com.trade.tradelicense.application.common;

import com.trade.tradelicense.domain.aggregates.TradeLicense;

public interface TradeLicenseRepositoryPort {
    TradeLicense save(TradeLicense tradeLicense);
}
