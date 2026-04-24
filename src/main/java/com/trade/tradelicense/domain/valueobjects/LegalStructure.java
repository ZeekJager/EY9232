package com.trade.tradelicense.domain.valueobjects;

/**
 * Value object (enum) representing the legal structure under which a business
 * operates.
 *
 * <p>{@code LegalStructure} is modelled as a Java {@code enum} so that it
 * behaves like a value object: it is immutable, has no identity of its own,
 * and two references to the same constant are always equal. Each constant
 * carries a human-readable {@link #displayName} and a brief
 * {@link #description} that can be shown in the UI or included in generated
 * documents.
 *
 * <h2>Typical legal requirements per structure</h2>
 * <ul>
 *   <li>{@link #SOLE_PROPRIETORSHIP} — simplest form; owner has unlimited
 *       personal liability. Usually requires only a trade license and owner
 *       ID documents.</li>
 *   <li>{@link #LLC} — limited-liability company; requires a Memorandum of
 *       Association (MOA), shareholder register, and a minimum share capital
 *       deposit depending on the jurisdiction.</li>
 *   <li>{@link #CORPORATION} — formal corporate entity (C-Corp / JSC / PLC);
 *       requires articles of incorporation, a board of directors, audited
 *       financials, and registration with the relevant corporate registry.</li>
 *   <li>{@link #PARTNERSHIP} — two or more partners sharing profits and
 *       liabilities. Requires a Partnership Agreement and, in many
 *       jurisdictions, registration of each partner's identity documents.</li>
 *   <li>{@link #BRANCH} — foreign-company branch; requires a parent-company
 *       certificate of incorporation, board resolution authorising the branch,
 *       and a local service agent agreement in some jurisdictions.</li>
 *   <li>{@link #FREE_ZONE_ENTITY} — company registered in a special economic
 *       / free-trade zone; governed by zone-specific regulations and typically
 *       requires a free-zone license rather than a mainland trade license.</li>
 * </ul>
 */
public enum LegalStructure {

    /**
     * A business owned and operated by a single individual with no legal
     * separation between the owner and the business.
     *
     * <p><strong>Key requirements:</strong> valid owner ID, trade name
     * registration, municipal trade license.
     */
    SOLE_PROPRIETORSHIP("Sole Proprietorship",
            "Single-owner business; owner bears unlimited personal liability."),

    /**
     * A Limited Liability Company — a hybrid structure where owners (members)
     * enjoy limited liability while retaining operational flexibility.
     *
     * <p><strong>Key requirements:</strong> Memorandum of Association (MOA),
     * shareholder register, minimum share capital per jurisdiction.
     */
    LLC("Limited Liability Company (LLC)",
            "Hybrid structure with limited liability for members and operational flexibility."),

    /**
     * A formal corporation (e.g. Joint-Stock Company, Public Limited Company,
     * or C-Corporation) with a separate legal personality, shareholders, and
     * a board of directors.
     *
     * <p><strong>Key requirements:</strong> Articles of Incorporation, board
     * resolution, shareholder register, audited financials, corporate registry
     * filing.
     */
    CORPORATION("Corporation",
            "Formal corporate entity with shareholders, a board of directors, and full legal personality."),

    /**
     * A business owned by two or more partners who share profits, losses, and
     * management responsibilities according to a Partnership Agreement.
     *
     * <p><strong>Key requirements:</strong> signed Partnership Agreement,
     * identity documents of all partners.
     */
    PARTNERSHIP("Partnership",
            "Two or more partners sharing profits, losses, and management duties."),

    /**
     * A branch office of a foreign (non-resident) company, operating in the
     * local jurisdiction under the parent company's legal personality.
     *
     * <p><strong>Key requirements:</strong> parent-company certificate of
     * incorporation, board resolution authorising the branch, local service
     * agent agreement (jurisdiction-dependent).
     */
    BRANCH("Branch of Foreign Company",
            "Extension of a foreign parent company operating locally under the parent's legal personality."),

    /**
     * An entity registered within a special economic zone or free-trade zone,
     * subject to zone-specific regulations rather than mainland company law.
     *
     * <p><strong>Key requirements:</strong> free-zone authority approval,
     * zone-specific license, registered office within the zone.
     */
    FREE_ZONE_ENTITY("Free Zone Entity",
            "Company registered in a special economic / free-trade zone under zone-specific regulations.");

    // -------------------------------------------------------------------------

    /**
     * Human-readable name suitable for display in UIs and printed documents.
     */
    private final String displayName;

    /**
     * Brief description of the legal structure, including liability and
     * governance characteristics.
     */
    private final String description;

    LegalStructure(String displayName, String description) {
        this.displayName = displayName;
        this.description = description;
    }

    /**
     * Returns the human-readable display name of this legal structure
     * (e.g. {@code "Limited Liability Company (LLC)"}).
     *
     * @return display name; never {@code null}
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     * Returns a brief description of this legal structure's characteristics
     * and typical legal requirements.
     *
     * @return description; never {@code null}
     */
    public String getDescription() {
        return description;
    }

    /**
     * Returns the display name, making {@code LegalStructure} convenient to
     * use in logging and UI contexts without explicit getter calls.
     *
     * @return {@link #displayName}
     */
    @Override
    public String toString() {
        return displayName;
    }
}
