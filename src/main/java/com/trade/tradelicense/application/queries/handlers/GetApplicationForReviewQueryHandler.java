package com.trade.tradelicense.application.queries.handlers;

import com.trade.tradelicense.application.exceptions.ApplicationNotFoundException;
import com.trade.tradelicense.application.queries.GetApplicationForReviewQuery;
import com.trade.tradelicense.domain.aggregate.TradeLicenseApplication;
import com.trade.tradelicense.domain.repository.ITradeLicenseApplicationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Handles the {@link GetApplicationForReviewQuery} by loading a specific application
 * for a reviewer to inspect.
 */
@Service
@RequiredArgsConstructor
public class GetApplicationForReviewQueryHandler {

    private final ITradeLicenseApplicationRepository applicationRepository;

    /**
     * Executes the query and returns the matching application.
     *
     * @param query the query; must not be {@code null}
     * @return the {@link TradeLicenseApplication} domain object
     * @throws ApplicationNotFoundException if no application is found with the given ID
     */
    @Transactional(readOnly = true)
    public TradeLicenseApplication handle(GetApplicationForReviewQuery query) {
        return applicationRepository.findById(query.getApplicationId())
                .orElseThrow(() -> ApplicationNotFoundException.forId("Application", query.getApplicationId()));
    }
}
