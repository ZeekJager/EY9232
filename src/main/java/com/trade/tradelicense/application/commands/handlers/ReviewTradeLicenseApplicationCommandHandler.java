package com.trade.tradelicense.application.commands.handlers;

import com.trade.tradelicense.application.commands.ReviewTradeLicenseApplicationCommand;
import com.trade.tradelicense.application.common.ReviewAction;
import com.trade.tradelicense.application.events.ApplicationReviewedEvent;
import com.trade.tradelicense.application.exceptions.ApplicationNotFoundException;
import com.trade.tradelicense.application.validators.ReviewActionValidator;
import com.trade.tradelicense.domain.ApplicationStatus;
import com.trade.tradelicense.domain.aggregate.TradeLicenseApplication;
import com.trade.tradelicense.domain.entities.AuditEntry;
import com.trade.tradelicense.domain.entities.User;
import com.trade.tradelicense.domain.repository.ITradeLicenseApplicationRepository;
import com.trade.tradelicense.domain.repository.IUserRepository;
import com.trade.tradelicense.domain.services.INotificationService;
import com.trade.tradelicense.domain.valueobjects.UserRole;
import com.trade.tradelicense.domain.valueobjects.WorkflowAction;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
 * Handles the {@link ReviewTradeLicenseApplicationCommand}.
 *
 * <p>Status transitions:
 * <ul>
 *   <li>{@link ReviewAction#ACCEPT}  → {@code PENDING → REVIEWED}</li>
 *   <li>{@link ReviewAction#REJECT}  → {@code PENDING → REJECTED}</li>
 *   <li>{@link ReviewAction#ADJUST}  → {@code PENDING → DRAFT}</li>
 * </ul>
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ReviewTradeLicenseApplicationCommandHandler {

    private final ITradeLicenseApplicationRepository applicationRepository;
    private final IUserRepository userRepository;
    private final INotificationService notificationService;
    private final ReviewActionValidator validator;
    private final ApplicationEventPublisher eventPublisher;

    /**
     * Executes the review command.
     *
     * @param command the review command; must not be {@code null}
     * @throws ApplicationNotFoundException    if the application or reviewer is not found
     * @throws com.trade.tradelicense.application.exceptions.InvalidWorkflowActionException
     *         if the application is not in a reviewable state or the actor lacks the REVIEWER role
     */
    @Transactional
    public void handle(ReviewTradeLicenseApplicationCommand command) {
        TradeLicenseApplication application = applicationRepository.findById(command.getApplicationId())
                .orElseThrow(() -> ApplicationNotFoundException.forId("Application", command.getApplicationId()));

        User reviewer = userRepository.findById(command.getReviewerId())
                .orElseThrow(() -> ApplicationNotFoundException.forId("Reviewer", command.getReviewerId()));

        validator.validate(application, reviewer);

        ApplicationStatus fromStatus = application.getStatus();
        ApplicationStatus toStatus = resolveStatus(command.getAction());
        WorkflowAction workflowAction = resolveWorkflowAction(command.getAction());

        application.setStatus(toStatus);
        application.setUpdatedAt(LocalDateTime.now());

        AuditEntry auditEntry = AuditEntry.builder()
                .actor(reviewer)
                .actorRole(UserRole.REVIEWER)
                .action(workflowAction)
                .fromStatus(fromStatus)
                .toStatus(toStatus)
                .performedAt(LocalDateTime.now())
                .comments(command.getComments())
                .build();
        application.getAuditTrail().add(auditEntry);

        applicationRepository.save(application);
        log.info("Application {} reviewed by {} with action {}", application.getId(), reviewer.getId(), command.getAction());

        sendNotifications(command, application);

        eventPublisher.publishEvent(new ApplicationReviewedEvent(
                application.getId(),
                command.getAction(),
                reviewer.getId(),
                command.getComments(),
                LocalDateTime.now()));
    }

    private ApplicationStatus resolveStatus(ReviewAction action) {
        return switch (action) {
            case ACCEPT -> ApplicationStatus.REVIEWED;
            case REJECT -> ApplicationStatus.REJECTED;
            case ADJUST -> ApplicationStatus.DRAFT;
        };
    }

    private WorkflowAction resolveWorkflowAction(ReviewAction action) {
        return switch (action) {
            case ACCEPT -> WorkflowAction.ACCEPT;
            case REJECT -> WorkflowAction.REJECT;
            case ADJUST -> WorkflowAction.ADJUST;
        };
    }

    private void sendNotifications(ReviewTradeLicenseApplicationCommand command,
                                   TradeLicenseApplication application) {
        String applicantName = application.getApplicant().getName();
        switch (command.getAction()) {
            case ACCEPT ->
                    notificationService.notifyApprovers(application.getId(), applicantName);
            case ADJUST ->
                    notificationService.notifyApplicant(application.getId(),
                            "Your application requires adjustments. Reviewer comments: " + command.getComments());
            case REJECT ->
                    notificationService.notifyApplicant(application.getId(),
                            "Your application has been rejected. Reason: " + command.getComments());
        }
    }
}
