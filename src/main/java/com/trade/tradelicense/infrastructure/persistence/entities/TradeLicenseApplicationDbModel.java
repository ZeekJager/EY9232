package com.trade.tradelicense.infrastructure.persistence.entities;

import com.trade.tradelicense.domain.ApplicationStatus;
import com.trade.tradelicense.domain.valueobjects.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Flattened read model (plain POJO) representing a denormalised view of a
 * {@link com.trade.tradelicense.domain.aggregate.TradeLicenseApplication} for
 * reporting and summary queries.
 *
 * <p>This class is intentionally <em>not</em> a JPA entity — it is populated
 * by {@link com.trade.tradelicense.infrastructure.persistence.mappers.ApplicationEntityMapper}
 * from the domain aggregate and is used only as a lightweight projection for
 * read-side operations and the API layer.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TradeLicenseApplicationDbModel {

    /** Unique application identifier. */
    private UUID id;

    /** UUID of the applicant. */
    private UUID applicantId;

    /** Display name of the applicant. */
    private String applicantName;

    /** E-mail address of the applicant. */
    private String applicantEmail;

    /** UUID of the selected trade license type. */
    private UUID tradeLicenseTypeId;

    /** Human-readable name of the trade license type. */
    private String tradeLicenseTypeName;

    /** Current workflow status of the application. */
    private ApplicationStatus status;

    /** Number of attachments on the application. */
    private int attachmentCount;

    /** Status of the linked payment, or {@code null} if no payment exists. */
    private PaymentStatus paymentStatus;

    /** Currency of the linked payment. */
    private String paymentCurrency;

    /** Amount of the linked payment. */
    private BigDecimal paymentAmount;

    /** Timestamp at which the application record was created. */
    private LocalDateTime createdAt;

    /** Timestamp of the most recent update. */
    private LocalDateTime updatedAt;
}
