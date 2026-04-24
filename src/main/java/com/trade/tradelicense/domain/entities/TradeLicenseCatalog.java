package com.trade.tradelicense.domain.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * <strong>Aggregate root</strong> for the Trade License Catalog.
 *
 * <p>{@code TradeLicenseCatalog} is the authoritative source for which
 * {@link TradeLicenseType}s (trade license templates) are available to
 * applicants within a given jurisdiction or municipality.  It owns the
 * collection of {@link TradeLicenseType} entries and enforces the invariant
 * that no two license types within the same catalog share the same name.
 *
 * <h2>Aggregate boundary</h2>
 * <ul>
 *   <li>{@link TradeLicenseType}s — the catalog entries managed by this root.</li>
 * </ul>
 *
 * <h2>Usage in the workflow</h2>
 * When an applicant begins a new trade-license application they must select a
 * {@link TradeLicenseType} from a catalog.  The catalog is read-only from the
 * application's perspective; only catalog administrators mutate it through this
 * aggregate root.
 *
 * @see TradeLicenseType
 */
@Entity
@Table(name = "trade_license_catalogs")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TradeLicenseCatalog {

    /**
     * Surrogate primary key identifying this catalog.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    /**
     * Human-readable name of this catalog
     * (e.g. "Dubai Municipality Trade License Catalog").
     */
    @Column(nullable = false, unique = true)
    private String name;

    /**
     * Optional description providing context about the catalog
     * (e.g. the issuing authority or applicable regulations).
     */
    @Column(length = 500)
    private String description;

    /**
     * All trade license types available in this catalog.
     *
     * <p>Managed exclusively through this aggregate root to ensure naming
     * uniqueness and referential integrity within the catalog boundary.
     */
    @Builder.Default
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "catalog_id")
    private List<TradeLicenseType> licenseTypes = new ArrayList<>();
}
