package com.trade.tradelicense.application.queries;

import lombok.Value;

import java.util.UUID;

/**
 * Query object used by an approver to fetch the details of a specific reviewed
 * application before making a final decision.
 */
@Value
public class GetApplicationForApprovalQuery {

    /** UUID of the application to load. */
    UUID applicationId;

    /** UUID of the approver requesting the details (used for access control). */
    UUID approverId;
}
