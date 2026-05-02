package com.trade.tradelicense.infrastructure.persistence;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "trade_licenses")
public class JpaTradeLicenseEntity {
    @Id
    private UUID id;
    private String licenseNumber;
    private UUID sourceApplicationId;
    private UUID applicantId;
    private String fullName;
    private String tradeLicenseType;
    private String commodity;
    private LocalDate issuedDate;

    protected JpaTradeLicenseEntity() {
    }

    public JpaTradeLicenseEntity(
            UUID id,
            String licenseNumber,
            UUID sourceApplicationId,
            UUID applicantId,
            String fullName,
            String tradeLicenseType,
            String commodity,
            LocalDate issuedDate
    ) {
        this.id = id;
        this.licenseNumber = licenseNumber;
        this.sourceApplicationId = sourceApplicationId;
        this.applicantId = applicantId;
        this.fullName = fullName;
        this.tradeLicenseType = tradeLicenseType;
        this.commodity = commodity;
        this.issuedDate = issuedDate;
    }

    public UUID getId() {
        return id;
    }

    public String getLicenseNumber() {
        return licenseNumber;
    }

    public UUID getSourceApplicationId() {
        return sourceApplicationId;
    }

    public UUID getApplicantId() {
        return applicantId;
    }

    public String getFullName() {
        return fullName;
    }

    public String getTradeLicenseType() {
        return tradeLicenseType;
    }

    public String getCommodity() {
        return commodity;
    }

    public LocalDate getIssuedDate() {
        return issuedDate;
    }
}
