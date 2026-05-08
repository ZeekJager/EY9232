package com.trade.tradelicense.api.dtos;

import com.trade.tradelicense.domain.ApplicationStatus;
import com.trade.tradelicense.domain.aggregate.TradeLicenseApplication;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Lightweight summary DTO for listing trade license applications.
 *
 * <p>Contains only the most important fields needed for list views. Use
 * {@link ApplicationDetailDTO} when the full application data is required.
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationSummaryDTO {

    private UUID id;
    private String applicantName;
    private String licenseTypeName;
    private ApplicationStatus status;
    private LocalDateTime createdAt;

    /**
     * Builds an {@code ApplicationSummaryDTO} from a domain aggregate.
     *
     * @param application the domain aggregate to map from; must not be {@code null}
     * @return a new summary DTO
     */
    public static ApplicationSummaryDTO from(TradeLicenseApplication application) {
        return ApplicationSummaryDTO.builder()
                .id(application.getId())
                .applicantName(application.getApplicant() != null
                        ? application.getApplicant().getName() : null)
                .licenseTypeName(application.getTradeLicenseType() != null
                        ? application.getTradeLicenseType().getName() : null)
                .status(application.getStatus())
                .createdAt(application.getCreatedAt())
                .build();
    }
}
