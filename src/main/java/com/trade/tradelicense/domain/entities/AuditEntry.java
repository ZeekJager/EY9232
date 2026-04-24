package com.trade.tradelicense.domain.entities;

import com.trade.tradelicense.domain.ApplicationStatus;
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
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * An immutable record of a single action performed on a
 * {@link TradeLicenseApplication} by an actor ({@link User}) during the
 * workflow lifecycle.
 *
 * <p>{@code AuditEntry} is owned by the {@link TradeLicenseApplication}
 * aggregate. A new entry is appended whenever a {@link WorkflowAction} causes
 * a status transition (e.g. {@code PENDING → REVIEWED}).
 *
 * <p><strong>Why {@code AuditEntry} references {@link User} (not a role-specific
 * entity)?</strong><br>
 * Because the actor is always a {@link User} with a {@link UserRole}. Storing
 * a reference to the single {@code User} entity — together with the role they
 * held at the moment of the action — provides a complete, self-contained audit
 * record without requiring polymorphic joins or separate actor tables. This
 * also means the audit trail remains accurate even if the user's role changes
 * after the fact.
 *
 * @see User
 * @see UserRole
 * @see WorkflowAction
 */
@Entity
@Table(name = "audit_entries")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuditEntry {

    /**
     * Surrogate primary key identifying this audit record.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    /**
     * The {@link User} who performed the action.
     *
     * <p>Using a single {@link User} entity here (rather than separate
     * {@code Approver}, {@code Reviewer}, or {@code Applicant} entities) keeps
     * the audit trail uniform: every record answers "who?" with the same type,
     * regardless of the actor's role.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "actor_id", nullable = false)
    private User actor;

    /**
     * The role the {@code actor} held at the time of this action. Stored
     * explicitly so that the audit trail remains accurate even if the user's
     * role changes later.
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserRole actorRole;

    /**
     * The workflow action that was performed (e.g. {@link WorkflowAction#SUBMIT},
     * {@link WorkflowAction#APPROVE}).
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private WorkflowAction action;

    /**
     * The application status immediately <em>before</em> this action was applied.
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ApplicationStatus fromStatus;

    /**
     * The application status immediately <em>after</em> this action was applied.
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ApplicationStatus toStatus;

    /**
     * Timestamp at which the action was recorded.
     */
    @Column(nullable = false)
    private LocalDateTime performedAt;

    /**
     * Optional free-text comments supplied by the actor (e.g. rejection reason,
     * reviewer notes).
     */
    @Column(length = 2000)
    private String comments;
}
