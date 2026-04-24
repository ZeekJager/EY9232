package com.trade.tradelicense.domain.entities;

/**
 * Represents every action that a {@link User} can perform on a
 * {@link TradeLicenseApplication} at a given workflow step.
 *
 * <p>Actions are grouped by the {@link UserRole} that is permitted to invoke them:
 * <ul>
 *   <li><strong>APPLICANT:</strong> {@link #SUBMIT}, {@link #CANCEL}</li>
 *   <li><strong>REVIEWER:</strong> {@link #ACCEPT}, {@link #REJECT},
 *       {@link #ADJUST}</li>
 *   <li><strong>APPROVER:</strong> {@link #APPROVE}, {@link #REJECT},
 *       {@link #REREVIEW}</li>
 * </ul>
 *
 * <p>The {@link TradeLicenseApplication} aggregate root is responsible for
 * enforcing that the acting {@link User}'s role authorises the requested
 * {@code WorkflowAction} and that the current
 * {@link com.trade.tradelicense.domain.ApplicationStatus} permits the transition.
 */
public enum WorkflowAction {

    // ── Applicant actions ──────────────────────────────────────────────────

    /** The applicant finalises and submits their draft application for review. */
    SUBMIT,

    /** The applicant withdraws their application before it is reviewed. */
    CANCEL,

    // ── Reviewer actions ───────────────────────────────────────────────────

    /**
     * The reviewer accepts the application, advancing it toward final approval.
     * Transitions status: {@code PENDING → REVIEWED}.
     */
    ACCEPT,

    /**
     * The reviewer or approver rejects the application.
     * Transitions status: {@code PENDING → REJECTED} (reviewer)
     * or {@code REVIEWED → REJECTED} (approver).
     */
    REJECT,

    /**
     * The reviewer requests adjustments from the applicant before proceeding.
     */
    ADJUST,

    // ── Approver actions ───────────────────────────────────────────────────

    /**
     * The approver grants the trade license.
     * Transitions status: {@code REVIEWED → APPROVED}.
     */
    APPROVE,

    /**
     * The approver sends the application back for re-review.
     * Transitions status: {@code REVIEWED → PENDING}.
     */
    REREVIEW
}
