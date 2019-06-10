package org.experteam.efatura.domain;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Amount {
    private BigDecimal amount;
    private String currencyCode;
}
