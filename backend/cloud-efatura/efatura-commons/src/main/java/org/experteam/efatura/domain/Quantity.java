package org.experteam.efatura.domain;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Quantity {
    private String unitCode;
    private BigDecimal value;
}
