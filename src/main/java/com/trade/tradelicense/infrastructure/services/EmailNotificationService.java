package com.trade.tradelicense.infrastructure.services;

import com.trade.tradelicense.domain.services.INotificationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Stub {@link INotificationService} implementation that logs notifications via
 * SLF4J.
 *
 * <p>Replace this with a real e-mail / SMS / push-notification provider
 * integration (e.g. JavaMail, SendGrid, AWS SNS) in a production environment.
 */
@Slf4j
@Service
public class EmailNotificationService implements INotificationService {

    /**
     * {@inheritDoc}
     *
     * <p>Logs a notification message targeted at all reviewers.
     */
    @Override
    public void notifyReviewers(UUID applicationId, String applicantName) {
        log.info("[NOTIFICATION] Reviewers: New application {} submitted by '{}' is awaiting review.",
                applicationId, applicantName);
    }

    /**
     * {@inheritDoc}
     *
     * <p>Logs a notification message targeted at all approvers.
     */
    @Override
    public void notifyApprovers(UUID applicationId, String applicantName) {
        log.info("[NOTIFICATION] Approvers: Application {} submitted by '{}' has passed review and awaits approval.",
                applicationId, applicantName);
    }

    /**
     * {@inheritDoc}
     *
     * <p>Logs a notification message targeted at the application's applicant.
     */
    @Override
    public void notifyApplicant(UUID applicationId, String message) {
        log.info("[NOTIFICATION] Applicant (application {}): {}", applicationId, message);
    }
}
