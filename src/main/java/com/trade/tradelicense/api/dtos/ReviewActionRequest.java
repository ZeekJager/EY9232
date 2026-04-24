package com.trade.tradelicense.api.dtos;

import com.trade.tradelicense.application.common.ReviewAction;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Request body DTO for a reviewer's decision on a pending application.
 *
 * @see com.trade.tradelicense.api.controllers.ReviewerController
 */
@Getter
@Setter
@NoArgsConstructor
public class ReviewActionRequest {

    /** The review decision (ACCEPT, REJECT, or ADJUST). */
    @NotNull(message = "action must not be null")
    private ReviewAction action;

    /** Optional reviewer comments (e.g. rejection reason or adjustment instructions). */
    private String comments;
}
