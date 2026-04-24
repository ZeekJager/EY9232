package com.trade.tradelicense.infrastructure.common.logging;

import com.trade.tradelicense.domain.entities.AuditEntry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Infrastructure component that logs {@link AuditEntry} domain events to the
 * application's SLF4J logger.
 *
 * <p>In a production system this component could be extended to persist audit
 * logs to an external SIEM, an append-only audit database, or a message queue.
 */
@Slf4j
@Component
public class AuditLogger {

    /**
     * Logs the given {@link AuditEntry} at {@code INFO} level.
     *
     * @param entry the audit entry to log; must not be {@code null}
     */
    public void log(AuditEntry entry) {
        log.info("[AUDIT] actor={} role={} action={} fromStatus={} toStatus={} performedAt={} comments='{}'",
                entry.getActor() != null ? entry.getActor().getId() : "unknown",
                entry.getActorRole(),
                entry.getAction(),
                entry.getFromStatus(),
                entry.getToStatus(),
                entry.getPerformedAt(),
                entry.getComments());
    }
}
