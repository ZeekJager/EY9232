package com.trade.tradelicense.api.controllers;

import com.trade.tradelicense.api.common.ApiResponse;
import com.trade.tradelicense.api.dtos.ApplicationDetailDTO;
import com.trade.tradelicense.api.dtos.ApplicationSummaryDTO;
import com.trade.tradelicense.api.dtos.ApprovalActionRequest;
import com.trade.tradelicense.application.commands.ApproveTradeLicenseApplicationCommand;
import com.trade.tradelicense.application.commands.handlers.ApproveTradeLicenseApplicationCommandHandler;
import com.trade.tradelicense.application.queries.GetApplicationForApprovalQuery;
import com.trade.tradelicense.application.queries.handlers.GetApplicationForApprovalQueryHandler;
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
 * REST controller exposing the trade license approval workflow endpoints for
 * users with the {@link com.trade.tradelicense.domain.valueobjects.UserRole#APPROVER} role.
 *
 * <p>All endpoints expect the approver's identity in the {@code X-User-Id} header.
 */
@RestController
@RequestMapping("/api/approver")
@RequiredArgsConstructor
public class ApproverController {

    private final ApproveTradeLicenseApplicationCommandHandler approveHandler;
    private final GetApplicationForApprovalQueryHandler approvalQueryHandler;
    private final ITradeLicenseApplicationRepository applicationRepository;

    /**
     * Lists all applications in {@link ApplicationStatus#REVIEWED} status that are
     * waiting for final approval.
     *
     * <p>{@code GET /api/approver/tasks}
     *
     * @param userId the UUID of the authenticated approver
     * @return {@code 200 OK} with a list of reviewed application summaries
     */
    @GetMapping("/tasks")
    public ResponseEntity<ApiResponse<List<ApplicationSummaryDTO>>> listReviewedTasks(
            @RequestHeader("X-User-Id") UUID userId) {

        List<TradeLicenseApplication> reviewed = applicationRepository.findByStatus(ApplicationStatus.REVIEWED);
        List<ApplicationSummaryDTO> dtos = reviewed.stream()
                .map(ApplicationSummaryDTO::from)
                .collect(Collectors.toList());
        return ResponseEntity.ok(ApiResponse.ok(dtos));
    }

    /**
     * Retrieves the full details of a specific application for approval.
     *
     * <p>{@code GET /api/approver/applications/{id}}
     *
     * @param id     the UUID of the application
     * @param userId the UUID of the authenticated approver
     * @return {@code 200 OK} with the application detail DTO
     */
    @GetMapping("/applications/{id}")
    public ResponseEntity<ApiResponse<ApplicationDetailDTO>> getApplicationDetail(
            @PathVariable UUID id,
            @RequestHeader("X-User-Id") UUID userId) {

        TradeLicenseApplication application = approvalQueryHandler.handle(
                new GetApplicationForApprovalQuery(id, userId));
        return ResponseEntity.ok(ApiResponse.ok(ApplicationDetailDTO.from(application)));
    }

    /**
     * Submits an approval decision (APPROVE, REJECT, or REREVIEW) for a reviewed application.
     *
     * <p>{@code POST /api/approver/applications/{id}/approve}
     *
     * @param id      the UUID of the application to approve
     * @param userId  the UUID of the authenticated approver
     * @param request the approval decision and optional comments
     * @return {@code 200 OK} with no payload
     */
    @PostMapping("/applications/{id}/approve")
    public ResponseEntity<ApiResponse<Void>> approve(
            @PathVariable UUID id,
            @RequestHeader("X-User-Id") UUID userId,
            @Valid @RequestBody ApprovalActionRequest request) {

        approveHandler.handle(new ApproveTradeLicenseApplicationCommand(
                id,
                userId,
                request.getAction(),
                request.getComments()));

        return ResponseEntity.ok(ApiResponse.ok(null, "Approval action submitted successfully."));
    }
}
