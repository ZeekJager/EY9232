package com.trade.tradelicense.domain.valueobjects;

import com.trade.tradelicense.domain.entities.WorkflowAction;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * Value object capturing the outcome of a reviewer's or approver's action on a
 * {@link com.trade.tradelicense.domain.entities.TradeLicenseApplication}.
 *
 * <p>A {@code Decision} couples the {@link WorkflowAction} that was taken with
 * an optional free-text justification.  It is typically produced by domain
 * methods on the application aggregate (e.g. {@code review()} or
 * {@code approve()}) and can be persisted as part of an
 * {@link com.trade.tradelicense.domain.entities.AuditEntry}.
 *
 * <p>As a value object, {@code Decision} is immutable and equality is based
 * solely on its field values — two decisions are the same if they represent the
 * same action with the same comment.
 */
@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public final class Decision {

    /**
     * The workflow action that constitutes this decision
     * (e.g. {@link WorkflowAction#ACCEPT}, {@link WorkflowAction#REJECT},
     * {@link WorkflowAction#APPROVE}).
     */
    private final WorkflowAction action;

    /**
     * Optional free-text reason or notes provided by the decision-maker
     * (e.g. rejection reason, conditions for approval).  May be {@code null}.
     */
    private final String comments;
}
