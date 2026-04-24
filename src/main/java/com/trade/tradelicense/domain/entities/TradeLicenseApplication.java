package com.trade.tradelicense.domain.entities;

import com.trade.tradelicense.domain.ApplicationStatus;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.OrderBy;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * <strong>Aggregate root</strong> for the Trade License Workflow.
 *
 * <p>{@code TradeLicenseApplication} is the central entity that governs the
 * full lifecycle of a trade-license request — from initial draft through
 * submission, review, and final approval or rejection. All state transitions
 * are driven by {@link WorkflowAction}s performed by {@link User}s whose
 * {@link UserRole} is validated against the current
 * {@link ApplicationStatus}.
 *
 * <h2>Aggregate boundary</h2>
 * The following objects live inside this aggregate's consistency boundary:
 * <ul>
 *   <li>{@link TradeLicenseType} — the selected license type (referenced by ID)</li>
 *   <li>{@link Attachment}s — uploaded supporting documents</li>
 *   <li>{@link Payment} — the fee payment for this application</li>
 *   <li>{@link AuditEntry}s — immutable history of every action taken</li>
 * </ul>
 *
 * <h2>Key invariants</h2>
 * <ul>
 *   <li>An application may only be submitted when all
 *       {@link TradeLicenseType#getRequiredDocumentTypes()} are present as
 *       {@link Attachment}s and the {@link Payment} status is
 *       {@link PaymentStatus#SETTLED}.</li>
 *   <li>Review actions ({@link WorkflowAction#ACCEPT}, {@link WorkflowAction#REJECT},
 *       {@link WorkflowAction#ADJUST}) are only permitted when the status is
 *       {@link ApplicationStatus#PENDING} and the actor's role is
 *       {@link UserRole#REVIEWER}.</li>
 *   <li>Approval actions ({@link WorkflowAction#APPROVE},
 *       {@link WorkflowAction#REREVIEW}) are only permitted when the status is
 *       {@link ApplicationStatus#REVIEWED} and the actor's role is
 *       {@link UserRole#APPROVER}.</li>
 * </ul>
 *
 * <h2>Actors are Users, not role-specific entities</h2>
 * The {@code applicant}, and every actor recorded in {@link AuditEntry}, is a
 * {@link User} with an assigned {@link UserRole}. This design avoids creating
 * separate {@code Applicant}, {@code Reviewer}, and {@code Approver} entity
 * classes — keeping the model simple, avoiding identity duplication, and
 * allowing the same person to participate in different capacities across
 * different applications.
 *
 * @see User
 * @see UserRole
 * @see WorkflowAction
 * @see ApplicationStatus
 */
@Entity
@Table(name = "trade_license_applications")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TradeLicenseApplication {

    /**
     * Surrogate primary key — the stable identity of this application across
     * its entire lifecycle.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    /**
     * The {@link User} (with role {@link UserRole#APPLICANT}) who created and
     * owns this application.
     *
     * <p>Stored as a reference to the single {@link User} entity rather than a
     * dedicated {@code Applicant} class, in line with the role-based modelling
     * strategy described in {@link UserRole}.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "applicant_id", nullable = false)
    private User applicant;

    /**
     * The type of trade license being applied for. Determines which document
     * types are required.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "trade_license_type_id", nullable = false)
    private TradeLicenseType tradeLicenseType;

    /**
     * Current status of the application in the workflow lifecycle.
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ApplicationStatus status;

    /**
     * Documents uploaded by the applicant in support of this application.
     * The aggregate validates that all
     * {@link TradeLicenseType#getRequiredDocumentTypes()} are covered before
     * allowing submission.
     */
    @Builder.Default
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "application_id")
    private List<Attachment> attachments = new ArrayList<>();

    /**
     * The fee payment associated with this application. Must be in
     * {@link PaymentStatus#SETTLED} state before the application can be
     * submitted.
     */
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "payment_id")
    private Payment payment;

    /**
     * Ordered history of all actions performed on this application.
     * New entries are appended by the aggregate root as status transitions occur.
     * Each entry references the {@link User} actor and records their role at
     * the time of the action.
     */
    @Builder.Default
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "application_id")
    @OrderBy("performedAt ASC")
    private List<AuditEntry> auditTrail = new ArrayList<>();

    /**
     * Timestamp at which this application record was first created.
     */
    @Column(nullable = false)
    private LocalDateTime createdAt;

    /**
     * Timestamp of the most recent update to this application (status change,
     * attachment added, etc.).
     */
    @Column(nullable = false)
    private LocalDateTime updatedAt;
}
