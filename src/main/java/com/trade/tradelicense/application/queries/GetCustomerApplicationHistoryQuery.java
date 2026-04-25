package com.trade.tradelicense.application.queries;

import com.trade.tradelicense.domain.ApplicationStatus;
import lombok.Value;

import java.util.UUID;

/**
 * Query object used to retrieve a customer's application history, with an
 * optional status filter.
 */
@Value
public class GetCustomerApplicationHistoryQuery {

    /** UUID of the applicant whose applications are being listed. */
    UUID applicantId;

    /**
     * Optional status filter. When {@code null}, all applications for the
     * applicant are returned regardless of status.
     */
    ApplicationStatus statusFilter;
}
