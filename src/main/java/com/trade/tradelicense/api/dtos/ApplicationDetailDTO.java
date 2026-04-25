package com.trade.tradelicense.api.dtos;

import com.trade.tradelicense.domain.ApplicationStatus;
import com.trade.tradelicense.domain.aggregate.TradeLicenseApplication;
import com.trade.tradelicense.domain.entities.AuditEntry;
import com.trade.tradelicense.domain.entities.Payment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Detailed DTO for a single trade license application, used in the application
 * detail view by all workflow participants (applicant, reviewer, approver).
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationDetailDTO {

    private UUID id;
    private String applicantName;
    private String applicantEmail;
    private String licenseTypeName;
    private ApplicationStatus status;
    private List<String> attachmentFileNames;
    private String paymentStatus;
    private String paymentCurrency;
    private BigDecimal paymentAmount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<AuditEntryDTO> auditTrail;

    /**
     * Builds an {@code ApplicationDetailDTO} from a fully hydrated domain aggregate.
     *
     * @param application the domain aggregate to map from; must not be {@code null}
     * @return a new detail DTO
     */
    public static ApplicationDetailDTO from(TradeLicenseApplication application) {
        Payment payment = application.getPayment();

        List<String> fileNames = application.getAttachments() == null
                ? Collections.emptyList()
                : application.getAttachments().stream()
                        .map(att -> att.getFileName())
                        .collect(Collectors.toList());

        List<AuditEntryDTO> auditDtos = application.getAuditTrail() == null
                ? Collections.emptyList()
                : application.getAuditTrail().stream()
                        .map(AuditEntryDTO::from)
                        .collect(Collectors.toList());

        return ApplicationDetailDTO.builder()
                .id(application.getId())
                .applicantName(application.getApplicant() != null
                        ? application.getApplicant().getName() : null)
                .applicantEmail(application.getApplicant() != null
                        ? application.getApplicant().getEmail() : null)
                .licenseTypeName(application.getTradeLicenseType() != null
                        ? application.getTradeLicenseType().getName() : null)
                .status(application.getStatus())
                .attachmentFileNames(fileNames)
                .paymentStatus(payment != null ? payment.getStatus().name() : null)
                .paymentCurrency(payment != null ? payment.getCurrency() : null)
                .paymentAmount(payment != null ? payment.getAmount() : null)
                .createdAt(application.getCreatedAt())
                .updatedAt(application.getUpdatedAt())
                .auditTrail(auditDtos)
                .build();
    }

    // ── Nested audit entry DTO ─────────────────────────────────────────────

    /**
     * Flattened representation of a single {@link AuditEntry} for API responses.
     */
    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AuditEntryDTO {

        private String actorName;
        private String actorRole;
        private String action;
        private String fromStatus;
        private String toStatus;
        private LocalDateTime performedAt;
        private String comments;

        /**
         * Maps an {@link AuditEntry} domain entity to this DTO.
         *
         * @param entry the audit entry; must not be {@code null}
         * @return a new {@code AuditEntryDTO}
         */
        public static AuditEntryDTO from(AuditEntry entry) {
            return AuditEntryDTO.builder()
                    .actorName(entry.getActor() != null ? entry.getActor().getName() : null)
                    .actorRole(entry.getActorRole() != null ? entry.getActorRole().name() : null)
                    .action(entry.getAction() != null ? entry.getAction().name() : null)
                    .fromStatus(entry.getFromStatus() != null ? entry.getFromStatus().name() : null)
                    .toStatus(entry.getToStatus() != null ? entry.getToStatus().name() : null)
                    .performedAt(entry.getPerformedAt())
                    .comments(entry.getComments())
                    .build();
        }
    }
}
