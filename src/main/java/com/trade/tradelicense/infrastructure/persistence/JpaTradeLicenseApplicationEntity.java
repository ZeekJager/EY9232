package com.trade.tradelicense.infrastructure.persistence;

import com.trade.tradelicense.domain.enums.ApplicationStatus;
import com.trade.tradelicense.domain.enums.DocumentStatus;
import com.trade.tradelicense.domain.enums.PaymentStatus;
import com.trade.tradelicense.domain.enums.UserRole;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "trade_license_applications")
public class JpaTradeLicenseApplicationEntity {
    @Id
    private UUID id;
    private UUID applicantId;

    @Enumerated(EnumType.STRING)
    private UserRole applicantRole;

    private String fullName;
    private String nationalIdNumber;
    private String tinNumber;
    private String email;
    private String phoneNumber;
    private String tradeLicenseType;
    private String commodity;
    private UUID documentId;
    private String documentType;
    private String documentReference;

    @Enumerated(EnumType.STRING)
    private DocumentStatus documentStatus;

    private LocalDateTime documentUploadedAt;
    private UUID paymentSettlementId;
    private BigDecimal paymentAmount;
    private String paymentCurrency;
    private String paymentReference;

    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;

    private LocalDateTime paymentSettledAt;

    @Enumerated(EnumType.STRING)
    private ApplicationStatus status;

    protected JpaTradeLicenseApplicationEntity() {
    }

    public JpaTradeLicenseApplicationEntity(
            UUID id,
            UUID applicantId,
            UserRole applicantRole,
            String fullName,
            String nationalIdNumber,
            String tinNumber,
            String email,
            String phoneNumber,
            String tradeLicenseType,
            String commodity,
            UUID documentId,
            String documentType,
            String documentReference,
            DocumentStatus documentStatus,
            LocalDateTime documentUploadedAt,
            UUID paymentSettlementId,
            BigDecimal paymentAmount,
            String paymentCurrency,
            String paymentReference,
            PaymentStatus paymentStatus,
            LocalDateTime paymentSettledAt,
            ApplicationStatus status
    ) {
        this.id = id;
        this.applicantId = applicantId;
        this.applicantRole = applicantRole;
        this.fullName = fullName;
        this.nationalIdNumber = nationalIdNumber;
        this.tinNumber = tinNumber;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.tradeLicenseType = tradeLicenseType;
        this.commodity = commodity;
        this.documentId = documentId;
        this.documentType = documentType;
        this.documentReference = documentReference;
        this.documentStatus = documentStatus;
        this.documentUploadedAt = documentUploadedAt;
        this.paymentSettlementId = paymentSettlementId;
        this.paymentAmount = paymentAmount;
        this.paymentCurrency = paymentCurrency;
        this.paymentReference = paymentReference;
        this.paymentStatus = paymentStatus;
        this.paymentSettledAt = paymentSettledAt;
        this.status = status;
    }

    public UUID getId() {
        return id;
    }

    public UUID getApplicantId() {
        return applicantId;
    }

    public UserRole getApplicantRole() {
        return applicantRole;
    }

    public String getFullName() {
        return fullName;
    }

    public String getNationalIdNumber() {
        return nationalIdNumber;
    }

    public String getTinNumber() {
        return tinNumber;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getTradeLicenseType() {
        return tradeLicenseType;
    }

    public String getCommodity() {
        return commodity;
    }

    public UUID getDocumentId() {
        return documentId;
    }

    public String getDocumentType() {
        return documentType;
    }

    public String getDocumentReference() {
        return documentReference;
    }

    public DocumentStatus getDocumentStatus() {
        return documentStatus;
    }

    public LocalDateTime getDocumentUploadedAt() {
        return documentUploadedAt;
    }

    public UUID getPaymentSettlementId() {
        return paymentSettlementId;
    }

    public BigDecimal getPaymentAmount() {
        return paymentAmount;
    }

    public String getPaymentCurrency() {
        return paymentCurrency;
    }

    public String getPaymentReference() {
        return paymentReference;
    }

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public LocalDateTime getPaymentSettledAt() {
        return paymentSettledAt;
    }

    public ApplicationStatus getStatus() {
        return status;
    }
}
