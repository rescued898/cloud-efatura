package org.experteam.efatura.cloud.oracle.payload;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class SendInvoiceToCloudRequest {
    @NotEmpty
    private String uuid;
}
