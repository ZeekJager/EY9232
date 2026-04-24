package com.trade.tradelicense.domain.entities;

/**
 * Enum representing the possible roles a {@link User} can hold within the
 * Trade License Workflow.
 *
 * <p><strong>Design rationale — why a single {@code User} entity with a role field
 * is preferred over separate {@code Approver}, {@code Reviewer}, and
 * {@code Applicant} entities:</strong>
 * <ul>
 *   <li><em>Single source of truth:</em> All actors in the system share the same
 *       identity attributes (id, name, email). Duplicating these fields across
 *       three classes violates DRY and creates synchronisation overhead.</li>
 *   <li><em>Role reassignment:</em> A real user may act as an APPLICANT on one
 *       submission and as a REVIEWER on another. Separate classes would force
 *       multiple records for the same physical person.</li>
 *   <li><em>Simpler permission logic:</em> Domain invariants (e.g. "only a
 *       REVIEWER may call accept()") can be enforced with a single
 *       {@code user.getRole()} check rather than an {@code instanceof} hierarchy
 *       that grows with each new role.</li>
 *   <li><em>Audit-trail clarity:</em> {@link AuditEntry} can reference one
 *       {@link User} and store their role <em>at the time of the action</em>,
 *       preserving historical context without polymorphic joins.</li>
 * </ul>
 */
public enum UserRole {

    /**
     * The citizen or business entity that initiates and submits trade-license
     * applications. Permitted workflow actions: {@code SUBMIT}, {@code CANCEL}.
     */
    APPLICANT,

    /**
     * The back-office staff member responsible for the first-level review of a
     * submitted application. Permitted workflow actions: {@code ACCEPT},
     * {@code REJECT}, {@code ADJUST}.
     */
    REVIEWER,

    /**
     * The authority that grants final approval or rejection of a reviewed
     * application. Permitted workflow actions: {@code APPROVE}, {@code REJECT},
     * {@code REREVIEW}.
     */
    APPROVER
}
