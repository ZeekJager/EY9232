package com.trade.tradelicense.domain.valueobjects;

/**
 * Lifecycle states of an issued {@link com.trade.tradelicense.domain.entities.License}.
 */
public enum LicenseStatus {

    /** The license has been issued and is currently valid. */
    ACTIVE,

    /** The license validity period has elapsed and it is no longer in force. */
    EXPIRED,

    /** The license has been revoked by an authority before its expiry date. */
    REVOKED,

    /** The license is temporarily suspended pending investigation or compliance. */
    SUSPENDED
}
