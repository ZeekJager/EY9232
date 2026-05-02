package com.trade.tradelicense.domain.valueobjects;

import java.util.Objects;
import java.util.UUID;

public record ApplicantId(UUID value) {
    public ApplicantId {
        Objects.requireNonNull(value, "Applicant id is required");
    }

    public static ApplicantId newId() {
        return new ApplicantId(UUID.randomUUID());
    }
}
