package com.trade.tradelicense.application.queries.handlers;

import com.trade.tradelicense.application.queries.GetCustomerApplicationHistoryQuery;
import com.trade.tradelicense.domain.aggregate.TradeLicenseApplication;
import com.trade.tradelicense.domain.repository.ITradeLicenseApplicationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Handles the {@link GetCustomerApplicationHistoryQuery}.
 *
 * <p>Returns all applications for the given applicant, optionally filtered by
 * status when {@link GetCustomerApplicationHistoryQuery#getStatusFilter()} is
 * not {@code null}.
 */
@Service
@RequiredArgsConstructor
public class GetCustomerApplicationHistoryQueryHandler {

    private final ITradeLicenseApplicationRepository applicationRepository;

    /**
     * Executes the query and returns the matching applications.
     *
     * @param query the query; must not be {@code null}
     * @return a (possibly empty) list of {@link TradeLicenseApplication} objects
     */
    @Transactional(readOnly = true)
    public List<TradeLicenseApplication> handle(GetCustomerApplicationHistoryQuery query) {
        List<TradeLicenseApplication> all = applicationRepository.findByApplicantId(query.getApplicantId());

        if (query.getStatusFilter() == null) {
            return all;
        }

        return all.stream()
                .filter(app -> app.getStatus() == query.getStatusFilter())
                .collect(Collectors.toList());
    }
}
