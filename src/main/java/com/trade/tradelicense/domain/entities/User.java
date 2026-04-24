package com.trade.tradelicense.domain.entities;

import com.trade.tradelicense.domain.valueobjects.UserRole;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

/**
 * Domain entity representing any human actor in the Trade License Workflow.
 *
 * <p><strong>Why one {@code User} entity instead of separate {@code Approver},
 * {@code Reviewer}, and {@code Applicant} classes?</strong>
 *
 * <p>In a DDD model, entities are distinguished by <em>identity</em>, not by the
 * role they play in a particular workflow step. Every participant — whether they
 * are submitting, reviewing, or approving a trade-license application — is first
 * and foremost a <em>person</em> with an identity, a name, and an e-mail address.
 * Their current responsibility within the workflow is captured by the
 * {@link UserRole} field, which is a <em>value</em> on the entity rather than a
 * separate class hierarchy.
 *
 * <p>Benefits of this approach:
 * <ul>
 *   <li>Avoids duplicating identity attributes across multiple classes.</li>
 *   <li>Supports users that hold or change roles over time without creating new
 *       records.</li>
 *   <li>Simplifies permission enforcement: domain operations check
 *       {@code user.getRole()} rather than performing {@code instanceof} casts.</li>
 *   <li>Makes {@link AuditEntry} straightforward: each entry stores the
 *       {@code User} and the role they held at the time of the action.</li>
 * </ul>
 *
 * @see UserRole
 * @see AuditEntry
 */
@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    /**
     * Surrogate primary key — the stable identity of this user across all
     * workflow interactions.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    /**
     * Full display name of the user (e.g. "Jane Smith").
     */
    @Column(nullable = false)
    private String name;

    /**
     * Unique e-mail address used to identify and contact the user.
     */
    @Column(nullable = false, unique = true)
    private String email;

    /**
     * The role this user plays in the Trade License Workflow.
     *
     * <p>Instead of creating separate {@code Approver}, {@code Reviewer}, and
     * {@code Applicant} entity classes, the role is stored as a
     * {@link UserRole} enum value. Domain logic (e.g. inside
     * {@link TradeLicenseApplication}) enforces which
     * {@link WorkflowAction}s and {@link com.trade.tradelicense.domain.ApplicationStatus}
     * transitions are permitted for each role.
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserRole role;
}
