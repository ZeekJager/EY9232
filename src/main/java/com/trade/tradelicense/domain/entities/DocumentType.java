package com.trade.tradelicense.domain.entities;

/**
 * Categorises the types of documents that can be attached to a
 * {@link TradeLicenseApplication}.
 *
 * <p>A {@link TradeLicenseType} specifies which {@code DocumentType}s are
 * <em>required</em> before an application may be submitted. The
 * {@link TradeLicenseApplication} aggregate enforces that all required
 * document types are present as {@link Attachment}s.
 */
public enum DocumentType {

    /** Government-issued photo identification (passport, national ID, etc.). */
    NATIONAL_ID,

    /** Official proof of the applicant's registered business address. */
    PROOF_OF_ADDRESS,

    /** Certificate confirming the legal registration of the business entity. */
    BUSINESS_REGISTRATION,

    /** Valid trade or professional license (if renewing or upgrading). */
    EXISTING_LICENSE,

    /** Any additional supporting document requested by the reviewer. */
    SUPPORTING_DOCUMENT
}
