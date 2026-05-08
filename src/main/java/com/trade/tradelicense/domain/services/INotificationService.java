package com.trade.tradelicense.domain.services;

import java.util.UUID;

/**
 * Domain service interface for sending notifications to workflow participants.
 *
 * <p>Implementations are responsible for dispatching messages via the
 * appropriate channel (e-mail, SMS, push notification, etc.) without
 * exposing delivery infrastructure to the domain or application layers.
 */
public interface INotificationService {

    /**
     * Notifies all users with the {@link com.trade.tradelicense.domain.valueobjects.UserRole#REVIEWER} role that a
     * new application has been submitted and is awaiting review.
     *
     * @param applicationId the UUID of the submitted application;
     *                      must not be {@code null}
     * @param applicantName the display name of the applicant;
     *                      must not be {@code null}
     */
    void notifyReviewers(UUID applicationId, String applicantName);

    /**
     * Notifies all users with the {@link com.trade.tradelicense.domain.valueobjects.UserRole#APPROVER} role that a
     * reviewed application is ready for final approval.
     *
     * @param applicationId the UUID of the reviewed application;
     *                      must not be {@code null}
     * @param applicantName the display name of the applicant;
     *                      must not be {@code null}
     */
    void notifyApprovers(UUID applicationId, String applicantName);

    /**
     * Sends a notification directly to the applicant regarding a status update
     * on their application.
     *
     * @param applicationId the UUID of the relevant application;
     *                      must not be {@code null}
     * @param message       a human-readable message to deliver to the applicant;
     *                      must not be {@code null}
     */
    void notifyApplicant(UUID applicationId, String message);
}
