package org.experteam.efatura.domain;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class TaxTotal {
    private Amount taxAmount;
    private Amount roundingAmount;
    private List<TaxSubtotal> taxSubtotal;

    public List<TaxSubtotal> getTaxSubtotal() {
        if (taxSubtotal == null)
            taxSubtotal = new ArrayList<>();

        return taxSubtotal;
    }
}
