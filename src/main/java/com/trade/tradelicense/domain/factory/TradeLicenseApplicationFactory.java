package com.trade.tradelicense.domain.factory;

import com.trade.tradelicense.domain.ApplicationStatus;
import com.trade.tradelicense.domain.aggregate.TradeLicenseApplication;
import com.trade.tradelicense.domain.entities.TradeLicenseType;
import com.trade.tradelicense.domain.entities.User;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * Factory for creating a new {@link TradeLicenseApplication} in its valid initial state.
 *
 * <p>A freshly created application is always in the
 * {@link ApplicationStatus#DRAFT} state with empty attachment and audit-trail
 * collections and no payment assigned yet.  Using this factory ensures that all
 * mandatory fields are supplied at construction time and that the initial
 * invariants of the aggregate root are satisfied from the very first instant.
 *
 * <p>The factory does <em>not</em> persist the application; callers are
 * responsible for passing the returned object to the appropriate repository.
 *
 * @see TradeLicenseApplication
 */
@Component
public class TradeLicenseApplicationFactory {

    /**
     * Creates and returns a new {@link TradeLicenseApplication} for the given
     * applicant and trade license type, in the {@link ApplicationStatus#DRAFT} state.
     *
     * @param applicant        the {@link User} (with role
     *                         {@link com.trade.tradelicense.domain.entities.UserRole#APPLICANT})
     *                         who is initiating the application; must not be {@code null}
     * @param tradeLicenseType the {@link TradeLicenseType} selected by the applicant;
     *                         must not be {@code null}
     * @return a new, unsaved {@link TradeLicenseApplication} aggregate root in the
     *         {@code DRAFT} state
     */
    public TradeLicenseApplication create(User applicant, TradeLicenseType tradeLicenseType) {
        LocalDateTime now = LocalDateTime.now();
        return TradeLicenseApplication.builder()
                .applicant(applicant)
                .tradeLicenseType(tradeLicenseType)
                .status(ApplicationStatus.DRAFT)
                .attachments(new ArrayList<>())
                .auditTrail(new ArrayList<>())
                .createdAt(now)
                .updatedAt(now)
                .build();
    }
}
