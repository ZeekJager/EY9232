package com.trade.tradelicense.application.commands.handlers;

import com.trade.tradelicense.application.commands.CancelTradeLicenseApplicationCommand;
import com.trade.tradelicense.application.exceptions.ApplicationNotFoundException;
import com.trade.tradelicense.application.exceptions.InvalidWorkflowActionException;
import com.trade.tradelicense.domain.ApplicationStatus;
import com.trade.tradelicense.domain.aggregate.TradeLicenseApplication;
import com.trade.tradelicense.domain.repository.ITradeLicenseApplicationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Handles the {@link CancelTradeLicenseApplicationCommand}.
 *
 * <p>Cancellation is permitted only when the application is in the
 * {@link ApplicationStatus#DRAFT} or {@link ApplicationStatus#PENDING} state,
 * and the requesting user must be the application's owner (applicant).
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CancelTradeLicenseApplicationCommandHandler {

    private final ITradeLicenseApplicationRepository applicationRepository;

    /**
     * Executes the cancel command by deleting the application if eligible.
     *
     * @param command the cancel command; must not be {@code null}
     * @throws ApplicationNotFoundException   if the application is not found
     * @throws InvalidWorkflowActionException if the requesting user is not the owner, or
     *                                        the application is not in a cancellable state
     */
    @Transactional
    public void handle(CancelTradeLicenseApplicationCommand command) {
        TradeLicenseApplication application = applicationRepository.findById(command.getApplicationId())
                .orElseThrow(() -> ApplicationNotFoundException.forId("Application", command.getApplicationId()));

        if (!application.getApplicant().getId().equals(command.getApplicantId())) {
            throw new InvalidWorkflowActionException(
                    "User '" + command.getApplicantId() + "' is not the owner of application '"
                            + command.getApplicationId() + "'.");
        }

        if (application.getStatus() != ApplicationStatus.DRAFT
                && application.getStatus() != ApplicationStatus.PENDING) {
            throw InvalidWorkflowActionException.statusMismatch("CANCEL", application.getStatus());
        }

        applicationRepository.deleteById(command.getApplicationId());
        log.info("Application {} cancelled by applicant {}", command.getApplicationId(), command.getApplicantId());
    }
}
