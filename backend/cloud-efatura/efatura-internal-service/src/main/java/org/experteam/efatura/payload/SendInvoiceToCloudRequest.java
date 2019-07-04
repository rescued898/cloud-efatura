package org.experteam.efatura.payload;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class SendInvoiceToCloudRequest {
    @NotEmpty
    private String uuid;
}
