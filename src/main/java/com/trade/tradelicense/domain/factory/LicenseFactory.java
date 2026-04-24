package com.trade.tradelicense.domain.factory;

import com.trade.tradelicense.domain.aggregate.TradeLicenseApplication;
import com.trade.tradelicense.domain.entities.License;
import com.trade.tradelicense.domain.entities.TradeLicenseType;
import com.trade.tradelicense.domain.entities.User;
import com.trade.tradelicense.domain.valueobjects.LicenseStatus;
import com.trade.tradelicense.domain.valueobjects.ValidityPeriod;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * Factory for creating a new {@link License} upon approval of a
 * {@link TradeLicenseApplication}.
 *
 * <p>A freshly created license is always in the {@link LicenseStatus#ACTIVE}
 * state. Using this factory ensures that all mandatory fields are populated at
 * construction time and that the initial invariants of the entity are satisfied
 * from the very first instant.
 *
 * <p>The factory does <em>not</em> persist the license; callers are responsible
 * for passing the returned object to the appropriate repository.
 *
 * @see License
 */
@Component
public class LicenseFactory {

    /**
     * Creates and returns a new {@link License} in the {@link LicenseStatus#ACTIVE}
     * state.
     *
     * @param licenseNumber    the unique, human-readable license number to assign;
     *                         must not be {@code null}
     * @param application      the approved {@link TradeLicenseApplication} that
     *                         triggered issuance; must not be {@code null}
     * @param tradeLicenseType the {@link TradeLicenseType} being granted; must not
     *                         be {@code null}
     * @param holder           the {@link User} to whom the license is issued; must
     *                         not be {@code null}
     * @param validityPeriod   the {@link ValidityPeriod} during which the license
     *                         is in force; must not be {@code null}
     * @return a new, unsaved {@link License} in the {@code ACTIVE} state
     */
    public License create(String licenseNumber,
                          TradeLicenseApplication application,
                          TradeLicenseType tradeLicenseType,
                          User holder,
                          ValidityPeriod validityPeriod) {
        return License.builder()
                .licenseNumber(licenseNumber)
                .application(application)
                .tradeLicenseType(tradeLicenseType)
                .holder(holder)
                .validityPeriod(validityPeriod)
                .issuedAt(LocalDateTime.now())
                .status(LicenseStatus.ACTIVE)
                .build();
    }
}
