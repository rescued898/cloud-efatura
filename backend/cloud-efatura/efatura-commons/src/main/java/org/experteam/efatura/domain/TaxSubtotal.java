package org.experteam.efatura.domain;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class TaxSubtotal {
    private Amount taxAmount;
    private Amount taxableAmount;
    private BigDecimal percent;
    private String taxCategoryName;
    private String taxTypeCode;
}
