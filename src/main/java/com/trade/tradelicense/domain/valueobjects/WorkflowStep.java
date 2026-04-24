package com.trade.tradelicense.domain.valueobjects;

/**
 * Enumerates the sequential steps of the Trade License Application workflow.
 *
 * <p>Each {@code WorkflowStep} represents a discrete phase that must be
 * completed — by either the applicant or a back-office actor — before the
 * workflow can advance.  The steps reflect the screens and actions visible
 * in the UI:
 * <ol>
 *   <li>{@link #SELECT_LICENSE} — applicant chooses a trade license type.</li>
 *   <li>{@link #ATTACH_DOCUMENTS} — applicant uploads all required supporting
 *       documents.</li>
 *   <li>{@link #SETTLE_PAYMENT} — applicant pays the application fee.</li>
 *   <li>{@link #SUBMIT_APPLICATION} — applicant formally submits the completed
 *       application for back-office processing.</li>
 *   <li>{@link #REVIEW} — reviewer performs first-level review.</li>
 *   <li>{@link #APPROVAL} — approver grants or rejects final approval.</li>
 * </ol>
 */
public enum WorkflowStep {

    /**
     * The applicant selects a {@link com.trade.tradelicense.domain.entities.TradeLicenseType}
     * from the catalog.
     */
    SELECT_LICENSE,

    /**
     * The applicant uploads all documents required by the selected
     * {@link com.trade.tradelicense.domain.entities.TradeLicenseType}.
     */
    ATTACH_DOCUMENTS,

    /**
     * The applicant settles the application fee payment (must reach
     * {@link com.trade.tradelicense.domain.entities.PaymentStatus#SETTLED}).
     */
    SETTLE_PAYMENT,

    /**
     * The applicant formally submits the completed application for back-office
     * review, transitioning it from {@code DRAFT} to {@code PENDING}.
     */
    SUBMIT_APPLICATION,

    /**
     * A reviewer (role: {@link com.trade.tradelicense.domain.entities.UserRole#REVIEWER})
     * performs first-level review and either accepts, rejects, or requests
     * adjustments.
     */
    REVIEW,

    /**
     * An approver (role: {@link com.trade.tradelicense.domain.entities.UserRole#APPROVER})
     * grants final approval or rejects the application.
     */
    APPROVAL
}
