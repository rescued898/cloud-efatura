package org.experteam.efatura.domain;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Setter;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@Document(collection = "ubl_invoices")
@TypeAlias("invoice")
public class BaseInvoice extends Invoice {

    @Setter(AccessLevel.NONE)
    private List<BaseInvoiceLine> invoiceLines;

    public List<BaseInvoiceLine> getInvoiceLines() {
        if (invoiceLines == null) {
            invoiceLines = new ArrayList<>();
        }

        return invoiceLines;
    }
}
