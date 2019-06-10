package org.experteam.efatura.domain;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;

import java.util.ArrayList;
import java.util.List;

@Data
public abstract class Invoice {
    protected InvoiceDirection direction;
    protected String profileId;

    @Indexed(unique = true)
    protected String invoiceNumber;
    protected String copyIndicator;

    @Id
    protected String uuid;

    protected String issueDate;
    protected String issueTime;
    protected String invoiceTypeCode;
    protected List<String> note;
    protected String documentCurrencyCode;
    protected Integer lineCountNumeric;
    protected Party accountingSupplierParty;
    protected Party accountingCustomerParty;

    @Setter(AccessLevel.NONE)
    protected List<TaxTotal> taxTotals;

    protected Amount lineExtensionAmount;
    protected Amount taxExclusiveAmount;
    protected Amount taxInclusiveAmount;
    protected Amount allowanceTotalAmount;
    protected Amount payableAmount;

    protected boolean processed;
    protected IntgStatus integrationStatus;

    public List<String> getNote() {
        if (note == null)
            note = new ArrayList<>();

        return note;
    }

    public List<TaxTotal> getTaxTotals() {
        if (taxTotals == null)
            taxTotals = new ArrayList<>();

        return taxTotals;
    }
}
