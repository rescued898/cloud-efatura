package org.experteam.efatura.cloud.oracle.domain;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Setter;
import org.experteam.efatura.domain.Invoice;
import org.springframework.data.annotation.Transient;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@Document(collection = "ubl_invoices")
@TypeAlias("invoice")
public class OracleCloudInvoice extends Invoice {

    private EfaturaConfig efaturaConfig;
    private Supplier supplier;

    @Transient
    private Long invoiceId;

    @Transient
    private Long loadRequestId;

    @Setter(AccessLevel.NONE)
    private List<OracleCloudInvoiceLine> invoiceLines;

    public List<OracleCloudInvoiceLine> getInvoiceLines() {
        if (invoiceLines == null)
            invoiceLines = new ArrayList<>();

        return invoiceLines;
    }

}
