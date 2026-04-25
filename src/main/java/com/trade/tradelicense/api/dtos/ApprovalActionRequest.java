package com.trade.tradelicense.api.dtos;

import com.trade.tradelicense.application.common.ApprovalAction;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Request body DTO for an approver's decision on a reviewed application.
 *
 * @see com.trade.tradelicense.api.controllers.ApproverController
 */
@Getter
@Setter
@NoArgsConstructor
public class ApprovalActionRequest {

    /** The approval decision (APPROVE, REJECT, or REREVIEW). */
    @NotNull(message = "action must not be null")
    private ApprovalAction action;

    /** Optional approver comments (e.g. rejection rationale). */
    private String comments;
}
