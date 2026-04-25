package com.trade.tradelicense.infrastructure.persistence.configurations;

/**
 * Holds database table-name constants used across the infrastructure persistence layer.
 *
 * <p>Centralising table names in one place prevents typos and makes future
 * renames a single-point-of-change operation.
 */
public final class TradeLicenseTableConfig {

    /** Table storing {@link com.trade.tradelicense.domain.aggregate.TradeLicenseApplication} records. */
    public static final String APPLICATIONS_TABLE = "trade_license_applications";

    /** Table storing {@link com.trade.tradelicense.domain.entities.License} records. */
    public static final String LICENSES_TABLE = "licenses";

    /** Table storing {@link com.trade.tradelicense.domain.entities.User} records. */
    public static final String USERS_TABLE = "users";

    /** Table storing {@link com.trade.tradelicense.domain.entities.TradeLicenseType} records. */
    public static final String LICENSE_TYPES_TABLE = "trade_license_types";

    /** Table storing {@link com.trade.tradelicense.domain.entities.Attachment} records. */
    public static final String ATTACHMENTS_TABLE = "attachments";

    /** Table storing {@link com.trade.tradelicense.domain.entities.Payment} records. */
    public static final String PAYMENTS_TABLE = "payments";

    /** Table storing {@link com.trade.tradelicense.domain.entities.AuditEntry} records. */
    public static final String AUDIT_ENTRIES_TABLE = "audit_entries";

    private TradeLicenseTableConfig() {
        // Constants-only class; no instantiation needed via Spring (declared as @Component for discoverability).
    }
}
