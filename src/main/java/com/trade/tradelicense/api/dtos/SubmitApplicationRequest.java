package com.trade.tradelicense.api.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

/**
 * Request body DTO for submitting a new trade license application.
 *
 * @see com.trade.tradelicense.api.controllers.CustomerApplicationController
 */
@Getter
@Setter
@NoArgsConstructor
public class SubmitApplicationRequest {

    /** UUID of the selected trade license type. */
    @NotNull(message = "tradeLicenseTypeId must not be null")
    private UUID tradeLicenseTypeId;

    /** UUIDs of the attachments to associate with this application. */
    @NotNull(message = "attachmentIds must not be null")
    private List<UUID> attachmentIds;

    /** External payment reference confirming that the fee has been settled. */
    @NotNull(message = "paymentReference must not be null")
    private String paymentReference;
}
