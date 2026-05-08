package com.trade.tradelicense.application.commands.handlers;

import com.trade.tradelicense.application.commands.ApproveTradeLicenseApplicationCommand;
import com.trade.tradelicense.application.common.ApprovalAction;
import com.trade.tradelicense.application.events.ApplicationApprovedEvent;
import com.trade.tradelicense.application.exceptions.ApplicationNotFoundException;
import com.trade.tradelicense.application.exceptions.InvalidWorkflowActionException;
import com.trade.tradelicense.domain.ApplicationStatus;
import com.trade.tradelicense.domain.aggregate.TradeLicenseApplication;
import com.trade.tradelicense.domain.entities.AuditEntry;
import com.trade.tradelicense.domain.entities.License;
import com.trade.tradelicense.domain.entities.User;
import com.trade.tradelicense.domain.factory.LicenseFactory;
import com.trade.tradelicense.domain.repository.ITradeLicenseApplicationRepository;
import com.trade.tradelicense.domain.repository.ITradeLicenseRepository;
import com.trade.tradelicense.domain.repository.IUserRepository;
import com.trade.tradelicense.domain.services.INotificationService;
import com.trade.tradelicense.domain.valueobjects.UserRole;
import com.trade.tradelicense.domain.valueobjects.ValidityPeriod;
import com.trade.tradelicense.domain.valueobjects.WorkflowAction;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Handles the {@link ApproveTradeLicenseApplicationCommand}.
 *
 * <p>Status transitions:
 * <ul>
 *   <li>{@link ApprovalAction#APPROVE}  → {@code REVIEWED → APPROVED} (issues a License)</li>
 *   <li>{@link ApprovalAction#REJECT}   → {@code REVIEWED → REJECTED}</li>
 *   <li>{@link ApprovalAction#REREVIEW} → {@code REVIEWED → PENDING}</li>
 * </ul>
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ApproveTradeLicenseApplicationCommandHandler {

    private final ITradeLicenseApplicationRepository applicationRepository;
    private final ITradeLicenseRepository licenseRepository;
    private final IUserRepository userRepository;
    private final INotificationService notificationService;
    private final LicenseFactory licenseFactory;
    private final ApplicationEventPublisher eventPublisher;

    /**
     * Executes the approval command.
     *
     * @param command the approval command; must not be {@code null}
     * @throws ApplicationNotFoundException      if the application or approver is not found
     * @throws InvalidWorkflowActionException    if the application is not in REVIEWED state
     *                                           or the actor lacks the APPROVER role
     */
    @Transactional
    public void handle(ApproveTradeLicenseApplicationCommand command) {
        TradeLicenseApplication application = applicationRepository.findById(command.getApplicationId())
                .orElseThrow(() -> ApplicationNotFoundException.forId("Application", command.getApplicationId()));

        User approver = userRepository.findById(command.getApproverId())
                .orElseThrow(() -> ApplicationNotFoundException.forId("Approver", command.getApproverId()));

        if (application.getStatus() != ApplicationStatus.REVIEWED) {
            throw InvalidWorkflowActionException.statusMismatch(command.getAction(), application.getStatus());
        }
        if (approver.getRole() != UserRole.APPROVER) {
            throw new InvalidWorkflowActionException(
                    "User '" + approver.getId() + "' does not have the APPROVER role.");
        }

        ApplicationStatus fromStatus = application.getStatus();
        ApplicationStatus toStatus = resolveStatus(command.getAction());
        WorkflowAction workflowAction = resolveWorkflowAction(command.getAction());

        application.setStatus(toStatus);
        application.setUpdatedAt(LocalDateTime.now());

        AuditEntry auditEntry = AuditEntry.builder()
                .actor(approver)
                .actorRole(UserRole.APPROVER)
                .action(workflowAction)
                .fromStatus(fromStatus)
                .toStatus(toStatus)
                .performedAt(LocalDateTime.now())
                .comments(command.getComments())
                .build();
        application.getAuditTrail().add(auditEntry);

        applicationRepository.save(application);
        log.info("Application {} processed by approver {} with action {}",
                application.getId(), approver.getId(), command.getAction());

        if (command.getAction() == ApprovalAction.APPROVE) {
            License license = issueLicense(application);
            notificationService.notifyApplicant(application.getId(),
                    "Congratulations! Your trade license has been approved. License number: "
                            + license.getLicenseNumber());
            eventPublisher.publishEvent(new ApplicationApprovedEvent(
                    application.getId(),
                    license.getId(),
                    application.getApplicant().getId(),
                    LocalDateTime.now()));
        } else if (command.getAction() == ApprovalAction.REJECT) {
            notificationService.notifyApplicant(application.getId(),
                    "Your application has been rejected. Reason: " + command.getComments());
        }
    }

    private License issueLicense(TradeLicenseApplication application) {
        String licenseNumber = "TL-" + LocalDate.now().getYear() + "-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        ValidityPeriod validityPeriod = new ValidityPeriod(LocalDate.now(), LocalDate.now().plusYears(1));
        License license = licenseFactory.create(
                licenseNumber,
                application,
                application.getTradeLicenseType(),
                application.getApplicant(),
                validityPeriod);
        return licenseRepository.save(license);
    }

    private ApplicationStatus resolveStatus(ApprovalAction action) {
        return switch (action) {
            case APPROVE  -> ApplicationStatus.APPROVED;
            case REJECT   -> ApplicationStatus.REJECTED;
            case REREVIEW -> ApplicationStatus.PENDING;
        };
    }

    private WorkflowAction resolveWorkflowAction(ApprovalAction action) {
        return switch (action) {
            case APPROVE  -> WorkflowAction.APPROVE;
            case REJECT   -> WorkflowAction.REJECT;
            case REREVIEW -> WorkflowAction.REREVIEW;
        };
    }
}
