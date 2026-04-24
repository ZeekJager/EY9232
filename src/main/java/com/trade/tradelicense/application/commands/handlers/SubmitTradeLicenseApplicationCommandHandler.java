package com.trade.tradelicense.application.commands.handlers;

import com.trade.tradelicense.application.commands.SubmitTradeLicenseApplicationCommand;
import com.trade.tradelicense.application.events.ApplicationSubmittedEvent;
import com.trade.tradelicense.application.exceptions.ApplicationNotFoundException;
import com.trade.tradelicense.application.validators.SubmitApplicationValidator;
import com.trade.tradelicense.domain.ApplicationStatus;
import com.trade.tradelicense.domain.aggregate.TradeLicenseApplication;
import com.trade.tradelicense.domain.entities.AuditEntry;
import com.trade.tradelicense.domain.entities.Payment;
import com.trade.tradelicense.domain.entities.TradeLicenseType;
import com.trade.tradelicense.domain.entities.User;
import com.trade.tradelicense.domain.factory.TradeLicenseApplicationFactory;
import com.trade.tradelicense.domain.repository.ITradeLicenseApplicationRepository;
import com.trade.tradelicense.domain.repository.ITradeLicenseTypeRepository;
import com.trade.tradelicense.domain.repository.IUserRepository;
import com.trade.tradelicense.domain.services.INotificationService;
import com.trade.tradelicense.domain.services.IPaymentGateway;
import com.trade.tradelicense.domain.valueobjects.PaymentStatus;
import com.trade.tradelicense.domain.valueobjects.UserRole;
import com.trade.tradelicense.domain.valueobjects.WorkflowAction;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Handles the {@link SubmitTradeLicenseApplicationCommand}.
 *
 * <p>Workflow steps:
 * <ol>
 *   <li>Validate the command (attachments and payment reference present).</li>
 *   <li>Verify payment with the external payment gateway.</li>
 *   <li>Load the applicant and the requested trade license type.</li>
 *   <li>Create a new {@link TradeLicenseApplication} in the {@code DRAFT} state
 *       via the domain factory.</li>
 *   <li>Attach a settled {@link Payment} to the application.</li>
 *   <li>Transition status to {@code PENDING} and record an audit entry.</li>
 *   <li>Persist and publish {@link ApplicationSubmittedEvent}.</li>
 * </ol>
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SubmitTradeLicenseApplicationCommandHandler {

    private final ITradeLicenseApplicationRepository applicationRepository;
    private final IUserRepository userRepository;
    private final ITradeLicenseTypeRepository licenseTypeRepository;
    private final IPaymentGateway paymentGateway;
    private final INotificationService notificationService;
    private final TradeLicenseApplicationFactory applicationFactory;
    private final SubmitApplicationValidator validator;
    private final ApplicationEventPublisher eventPublisher;

    /**
     * Executes the submit command and returns the new application's UUID.
     *
     * @param command the submit command; must not be {@code null}
     * @return the UUID of the newly created application
     * @throws com.trade.tradelicense.application.exceptions.InvalidWorkflowActionException
     *         if the command is invalid or payment cannot be verified
     * @throws ApplicationNotFoundException if the applicant or license type is not found
     */
    @Transactional
    public UUID handle(SubmitTradeLicenseApplicationCommand command) {
        validator.validate(command);

        boolean paymentVerified = paymentGateway.verifyPayment(command.getPaymentReference());
        if (!paymentVerified) {
            throw new com.trade.tradelicense.application.exceptions.InvalidWorkflowActionException(
                    "Payment reference '" + command.getPaymentReference() + "' could not be verified.");
        }

        User applicant = userRepository.findById(command.getApplicantId())
                .orElseThrow(() -> ApplicationNotFoundException.forId("Applicant", command.getApplicantId()));

        TradeLicenseType licenseType = licenseTypeRepository.findById(command.getTradeLicenseTypeId())
                .orElseThrow(() -> ApplicationNotFoundException.forId("TradeLicenseType", command.getTradeLicenseTypeId()));

        TradeLicenseApplication application = applicationFactory.create(applicant, licenseType);

        Payment payment = Payment.builder()
                .amount(java.math.BigDecimal.ZERO)
                .currency("AED")
                .status(PaymentStatus.SETTLED)
                .settledAt(LocalDateTime.now())
                .build();
        application.setPayment(payment);

        ApplicationStatus fromStatus = application.getStatus();
        application.setStatus(ApplicationStatus.PENDING);
        application.setUpdatedAt(LocalDateTime.now());

        AuditEntry auditEntry = AuditEntry.builder()
                .actor(applicant)
                .actorRole(UserRole.APPLICANT)
                .action(WorkflowAction.SUBMIT)
                .fromStatus(fromStatus)
                .toStatus(ApplicationStatus.PENDING)
                .performedAt(LocalDateTime.now())
                .comments("Application submitted by applicant.")
                .build();
        application.getAuditTrail().add(auditEntry);

        TradeLicenseApplication saved = applicationRepository.save(application);
        log.info("Application {} submitted by applicant {}", saved.getId(), applicant.getId());

        notificationService.notifyReviewers(saved.getId(), applicant.getName());

        eventPublisher.publishEvent(new ApplicationSubmittedEvent(
                saved.getId(),
                applicant.getId(),
                applicant.getName(),
                LocalDateTime.now()));

        return saved.getId();
    }
}
