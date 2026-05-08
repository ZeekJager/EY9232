package com.trade.tradelicense.api.controllers;

import com.trade.tradelicense.api.common.ApiResponse;
import com.trade.tradelicense.api.dtos.ApplicationDetailDTO;
import com.trade.tradelicense.api.dtos.ApplicationSummaryDTO;
import com.trade.tradelicense.api.dtos.SubmitApplicationRequest;
import com.trade.tradelicense.application.commands.CancelTradeLicenseApplicationCommand;
import com.trade.tradelicense.application.commands.SubmitTradeLicenseApplicationCommand;
import com.trade.tradelicense.application.commands.handlers.CancelTradeLicenseApplicationCommandHandler;
import com.trade.tradelicense.application.commands.handlers.SubmitTradeLicenseApplicationCommandHandler;
import com.trade.tradelicense.application.exceptions.ApplicationNotFoundException;
import com.trade.tradelicense.application.queries.GetCustomerApplicationHistoryQuery;
import com.trade.tradelicense.application.queries.handlers.GetCustomerApplicationHistoryQueryHandler;
import com.trade.tradelicense.domain.aggregate.TradeLicenseApplication;
import com.trade.tradelicense.domain.repository.ITradeLicenseApplicationRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.trade.tradelicense.domain.ApplicationStatus;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * REST controller exposing the trade license application workflow endpoints
 * for the applicant (customer) role.
 *
 * <p>All endpoints expect the caller's identity in the {@code X-User-Id} header.
 */
@RestController
@RequestMapping("/api/applications")
@RequiredArgsConstructor
public class CustomerApplicationController {

    private final SubmitTradeLicenseApplicationCommandHandler submitHandler;
    private final CancelTradeLicenseApplicationCommandHandler cancelHandler;
    private final GetCustomerApplicationHistoryQueryHandler historyQueryHandler;
    private final ITradeLicenseApplicationRepository applicationRepository;

    /**
     * Submits a new trade license application.
     *
     * <p>{@code POST /api/applications}
     *
     * @param userId  the UUID of the authenticated applicant (from {@code X-User-Id} header)
     * @param request the submit request body
     * @return {@code 201 Created} with the new application's UUID
     */
    @PostMapping
    public ResponseEntity<ApiResponse<UUID>> submit(
            @RequestHeader("X-User-Id") UUID userId,
            @Valid @RequestBody SubmitApplicationRequest request) {

        SubmitTradeLicenseApplicationCommand command = new SubmitTradeLicenseApplicationCommand(
                userId,
                request.getTradeLicenseTypeId(),
                request.getAttachmentIds(),
                request.getPaymentReference());

        UUID applicationId = submitHandler.handle(command);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.ok(applicationId, "Application submitted successfully."));
    }

    /**
     * Retrieves the details of a single application.
     *
     * <p>{@code GET /api/applications/{id}}
     *
     * @param id the UUID of the application
     * @return {@code 200 OK} with the application detail DTO
     */
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ApplicationDetailDTO>> getById(@PathVariable UUID id) {
        TradeLicenseApplication application = applicationRepository.findById(id)
                .orElseThrow(() -> ApplicationNotFoundException.forId("Application", id));
        return ResponseEntity.ok(ApiResponse.ok(ApplicationDetailDTO.from(application)));
    }

    /**
     * Cancels an application owned by the authenticated applicant.
     *
     * <p>{@code DELETE /api/applications/{id}}
     *
     * @param id     the UUID of the application to cancel
     * @param userId the UUID of the authenticated applicant
     * @return {@code 200 OK} with no payload
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> cancel(
            @PathVariable UUID id,
            @RequestHeader("X-User-Id") UUID userId) {

        cancelHandler.handle(new CancelTradeLicenseApplicationCommand(id, userId));
        return ResponseEntity.ok(ApiResponse.ok(null, "Application cancelled successfully."));
    }

    /**
     * Lists all applications for the authenticated applicant, with optional status filter.
     *
     * <p>{@code GET /api/applications?status=PENDING}
     *
     * @param userId       the UUID of the authenticated applicant
     * @param statusFilter optional {@link ApplicationStatus} to filter results
     * @return {@code 200 OK} with a list of application summaries
     */
    @GetMapping
    public ResponseEntity<ApiResponse<List<ApplicationSummaryDTO>>> listMyApplications(
            @RequestHeader("X-User-Id") UUID userId,
            @RequestParam(required = false) ApplicationStatus statusFilter) {

        List<TradeLicenseApplication> applications = historyQueryHandler.handle(
                new GetCustomerApplicationHistoryQuery(userId, statusFilter));

        List<ApplicationSummaryDTO> dtos = applications.stream()
                .map(ApplicationSummaryDTO::from)
                .collect(Collectors.toList());

        return ResponseEntity.ok(ApiResponse.ok(dtos));
    }
}
