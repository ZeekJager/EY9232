package com.trade.tradelicense.api.controllers;

import com.trade.tradelicense.api.common.ApiResponse;
import com.trade.tradelicense.api.dtos.ApplicationDetailDTO;
import com.trade.tradelicense.api.dtos.ApplicationSummaryDTO;
import com.trade.tradelicense.api.dtos.ReviewActionRequest;
import com.trade.tradelicense.application.commands.ReviewTradeLicenseApplicationCommand;
import com.trade.tradelicense.application.commands.handlers.ReviewTradeLicenseApplicationCommandHandler;
import com.trade.tradelicense.application.queries.GetApplicationForReviewQuery;
import com.trade.tradelicense.application.queries.handlers.GetApplicationForReviewQueryHandler;
import com.trade.tradelicense.domain.ApplicationStatus;
import com.trade.tradelicense.domain.aggregate.TradeLicenseApplication;
import com.trade.tradelicense.domain.repository.ITradeLicenseApplicationRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * REST controller exposing the trade license review workflow endpoints for
 * users with the {@link com.trade.tradelicense.domain.valueobjects.UserRole#REVIEWER} role.
 *
 * <p>All endpoints expect the reviewer's identity in the {@code X-User-Id} header.
 */
@RestController
@RequestMapping("/api/reviewer")
@RequiredArgsConstructor
public class ReviewerController {

    private final ReviewTradeLicenseApplicationCommandHandler reviewHandler;
    private final GetApplicationForReviewQueryHandler reviewQueryHandler;
    private final ITradeLicenseApplicationRepository applicationRepository;

    /**
     * Lists all applications in {@link ApplicationStatus#PENDING} status that are
     * waiting for review.
     *
     * <p>{@code GET /api/reviewer/tasks}
     *
     * @param userId the UUID of the authenticated reviewer
     * @return {@code 200 OK} with a list of pending application summaries
     */
    @GetMapping("/tasks")
    public ResponseEntity<ApiResponse<List<ApplicationSummaryDTO>>> listPendingTasks(
            @RequestHeader("X-User-Id") UUID userId) {

        List<TradeLicenseApplication> pending = applicationRepository.findByStatus(ApplicationStatus.PENDING);
        List<ApplicationSummaryDTO> dtos = pending.stream()
                .map(ApplicationSummaryDTO::from)
                .collect(Collectors.toList());
        return ResponseEntity.ok(ApiResponse.ok(dtos));
    }

    /**
     * Retrieves the full details of a specific application for review.
     *
     * <p>{@code GET /api/reviewer/applications/{id}}
     *
     * @param id     the UUID of the application
     * @param userId the UUID of the authenticated reviewer
     * @return {@code 200 OK} with the application detail DTO
     */
    @GetMapping("/applications/{id}")
    public ResponseEntity<ApiResponse<ApplicationDetailDTO>> getApplicationDetail(
            @PathVariable UUID id,
            @RequestHeader("X-User-Id") UUID userId) {

        TradeLicenseApplication application = reviewQueryHandler.handle(
                new GetApplicationForReviewQuery(id, userId));
        return ResponseEntity.ok(ApiResponse.ok(ApplicationDetailDTO.from(application)));
    }

    /**
     * Submits a review decision (ACCEPT, REJECT, or ADJUST) for an application.
     *
     * <p>{@code POST /api/reviewer/applications/{id}/review}
     *
     * @param id      the UUID of the application to review
     * @param userId  the UUID of the authenticated reviewer
     * @param request the review decision and optional comments
     * @return {@code 200 OK} with no payload
     */
    @PostMapping("/applications/{id}/review")
    public ResponseEntity<ApiResponse<Void>> review(
            @PathVariable UUID id,
            @RequestHeader("X-User-Id") UUID userId,
            @Valid @RequestBody ReviewActionRequest request) {

        reviewHandler.handle(new ReviewTradeLicenseApplicationCommand(
                id,
                userId,
                request.getAction(),
                request.getComments()));

        return ResponseEntity.ok(ApiResponse.ok(null, "Review action submitted successfully."));
    }
}
