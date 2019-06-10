package org.experteam.efatura.cloud.oracle.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.experteam.efatura.domain.InvoiceLine;
import org.springframework.data.annotation.TypeAlias;

@Data
@EqualsAndHashCode(callSuper = true)
@TypeAlias("line")
public class OracleCloudInvoiceLine extends InvoiceLine {

    private String lineLookupCode;
    private Boolean assetsTrackingFlag;
    private Boolean finalMatchFlag;

    //TODO: hard code ?
    public OracleCloudInvoiceLine() {
        this.lineLookupCode = "ITEM";
        this.finalMatchFlag = false;
        this.assetsTrackingFlag = false;
    }
}
