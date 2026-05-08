package com.trade.tradelicense.application.queries;

import lombok.Value;

import java.util.UUID;

/**
 * Query object used by a reviewer to fetch the details of a specific application
 * before acting on it.
 */
@Value
public class GetApplicationForReviewQuery {

    /** UUID of the application to load. */
    UUID applicationId;

    /** UUID of the reviewer requesting the details (used for access control). */
    UUID reviewerId;
}
