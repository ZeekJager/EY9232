package com.trade.tradelicense.domain.entities;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

/**
 * Catalog entity representing a specific type of trade license that can be
 * applied for (e.g. "Food &amp; Beverage", "General Trading", "Professional Services").
 *
 * <p>A {@link TradeLicenseType} acts as the template that defines which
 * {@link DocumentType}s must be attached before a
 * {@link TradeLicenseApplication} of this type can be submitted. The
 * {@link TradeLicenseApplication} aggregate root enforces this invariant.
 */
@Entity
@Table(name = "trade_license_types")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TradeLicenseType {

    /**
     * Surrogate primary key identifying this license type in the catalog.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Human-readable name of the trade license type (e.g. "Food &amp; Beverage").
     */
    @Column(nullable = false, unique = true)
    private String name;

    /**
     * The set of document types that <em>must</em> be supplied by the applicant
     * when applying for this license type. The
     * {@link TradeLicenseApplication} aggregate enforces completeness before
     * allowing submission.
     */
    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    private Set<DocumentType> requiredDocumentTypes;
}
