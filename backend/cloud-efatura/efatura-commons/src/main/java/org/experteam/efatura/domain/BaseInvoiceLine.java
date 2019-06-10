package org.experteam.efatura.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.TypeAlias;

@TypeAlias("line")
@Data
@EqualsAndHashCode(callSuper = true)
public class BaseInvoiceLine extends InvoiceLine {
}
