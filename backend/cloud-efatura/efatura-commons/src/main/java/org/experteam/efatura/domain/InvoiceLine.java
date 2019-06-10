package org.experteam.efatura.domain;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public abstract class InvoiceLine {
    private String id;
    private List<String> note;
    private Quantity invoicedQuantity;
    private Amount lineExtensionAmount;
    private List<TaxTotal> taxTotals;
    private Item item;

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
