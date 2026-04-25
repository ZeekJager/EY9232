package com.trade.tradelicense.infrastructure.persistence.mappers;

import com.trade.tradelicense.domain.aggregate.TradeLicenseApplication;
import com.trade.tradelicense.domain.entities.Payment;
import com.trade.tradelicense.infrastructure.persistence.entities.TradeLicenseApplicationDbModel;
import org.springframework.stereotype.Component;

/**
 * Maps between the {@link TradeLicenseApplication} domain aggregate and the
 * flattened {@link TradeLicenseApplicationDbModel} read model.
 *
 * <p>This mapper is used by infrastructure components to convert domain objects
 * into lightweight projections suitable for reporting and API responses.
 */
@Component
public class ApplicationEntityMapper {

    /**
     * Converts a {@link TradeLicenseApplication} aggregate root into a flattened
     * {@link TradeLicenseApplicationDbModel} read model.
     *
     * @param application the domain aggregate to convert; must not be {@code null}
     * @return a new {@link TradeLicenseApplicationDbModel}
     */
    public TradeLicenseApplicationDbModel toDbModel(TradeLicenseApplication application) {
        Payment payment = application.getPayment();

        return TradeLicenseApplicationDbModel.builder()
                .id(application.getId())
                .applicantId(application.getApplicant() != null ? application.getApplicant().getId() : null)
                .applicantName(application.getApplicant() != null ? application.getApplicant().getName() : null)
                .applicantEmail(application.getApplicant() != null ? application.getApplicant().getEmail() : null)
                .tradeLicenseTypeId(application.getTradeLicenseType() != null
                        ? application.getTradeLicenseType().getId() : null)
                .tradeLicenseTypeName(application.getTradeLicenseType() != null
                        ? application.getTradeLicenseType().getName() : null)
                .status(application.getStatus())
                .attachmentCount(application.getAttachments() != null ? application.getAttachments().size() : 0)
                .paymentStatus(payment != null ? payment.getStatus() : null)
                .paymentCurrency(payment != null ? payment.getCurrency() : null)
                .paymentAmount(payment != null ? payment.getAmount() : null)
                .createdAt(application.getCreatedAt())
                .updatedAt(application.getUpdatedAt())
                .build();
    }

    /**
     * Converts a {@link TradeLicenseApplicationDbModel} read model back to a
     * minimal domain aggregate representation (suitable for status queries).
     *
     * <p><strong>Note:</strong> This method produces a partially hydrated aggregate.
     * Full relationship traversal (attachments, audit trail, etc.) requires loading
     * the aggregate via the JPA repository directly.
     *
     * @param model the read model to convert; must not be {@code null}
     * @return a partially hydrated {@link TradeLicenseApplication}
     */
    public TradeLicenseApplication toDomain(TradeLicenseApplicationDbModel model) {
        return TradeLicenseApplication.builder()
                .id(model.getId())
                .status(model.getStatus())
                .createdAt(model.getCreatedAt())
                .updatedAt(model.getUpdatedAt())
                .build();
    }
}
