package com.trade.tradelicense.domain.entities;

import com.trade.tradelicense.domain.aggregate.TradeLicenseApplication;
import com.trade.tradelicense.domain.valueobjects.LicenseStatus;
import com.trade.tradelicense.domain.valueobjects.ValidityPeriod;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Domain entity representing a trade license that has been issued upon approval
 * of a {@link TradeLicenseApplication}.
 *
 * <p>A {@code License} is created when an {@link com.trade.tradelicense.domain.aggregate.TradeLicenseApplication}
 * reaches the {@link com.trade.tradelicense.domain.ApplicationStatus#APPROVED} state.
 * It records the official license number, the holder, the license type, the
 * validity window, and the current lifecycle status of the license.
 */
@Entity
@Table(name = "licenses")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class License {

    /**
     * Surrogate primary key identifying this license.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    /**
     * Unique, human-readable license number assigned at issuance
     * (e.g. {@code "TL-2025-000123"}).
     */
    @Column(nullable = false, unique = true)
    private String licenseNumber;

    /**
     * The approved application that resulted in the issuance of this license.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "application_id", nullable = false)
    private TradeLicenseApplication application;

    /**
     * The type of trade license that was granted.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "trade_license_type_id", nullable = false)
    private TradeLicenseType tradeLicenseType;

    /**
     * The {@link User} (applicant) to whom this license was issued.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "holder_id", nullable = false)
    private User holder;

    /**
     * The date range during which this license is valid.
     */
    @Embedded
    private ValidityPeriod validityPeriod;

    /**
     * Timestamp at which this license was officially issued.
     */
    @Column(nullable = false)
    private LocalDateTime issuedAt;

    /**
     * Current lifecycle status of the license.
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private LicenseStatus status;
}
