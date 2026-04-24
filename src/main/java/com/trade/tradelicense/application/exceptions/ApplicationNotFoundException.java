package com.trade.tradelicense.application.exceptions;

import java.util.UUID;

/**
 * Thrown when a requested {@link com.trade.tradelicense.domain.aggregate.TradeLicenseApplication}
 * (or related resource such as a {@link com.trade.tradelicense.domain.entities.User} or
 * {@link com.trade.tradelicense.domain.entities.TradeLicenseType}) cannot be found in the
 * repository.
 *
 * <p>The {@link com.trade.tradelicense.api.common.GlobalExceptionHandler} maps this
 * exception to an HTTP {@code 404 Not Found} response.
 */
public class ApplicationNotFoundException extends RuntimeException {

    /**
     * Constructs the exception with a descriptive message.
     *
     * @param message human-readable explanation of what was not found
     */
    public ApplicationNotFoundException(String message) {
        super(message);
    }

    /**
     * Convenience factory that formats a standard "not found by ID" message.
     *
     * @param resourceType human-readable resource type (e.g. {@code "Application"})
     * @param id           the UUID that was looked up
     * @return a new {@code ApplicationNotFoundException}
     */
    public static ApplicationNotFoundException forId(String resourceType, UUID id) {
        return new ApplicationNotFoundException(resourceType + " not found with id: " + id);
    }
}
