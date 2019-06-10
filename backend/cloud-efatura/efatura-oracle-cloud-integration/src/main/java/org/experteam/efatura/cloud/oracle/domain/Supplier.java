package org.experteam.efatura.cloud.oracle.domain;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.annotation.Transient;

@JsonIgnoreProperties(ignoreUnknown = true)
@Slf4j
@Data
@ToString
public class Supplier {

    @JsonAlias("SupplierId")
    private Long supplierId;

    @JsonAlias("Supplier")
    private String supplierName;

    @JsonAlias("SupplierNumber")
    private String supplierNumber;

    @Transient
    private Long partyId;

    @Transient
    private Long partySiteId;

    // @JsonAlias("TaxRegistrationCountryCode")
    // private String taxationCountry;

    @Transient
    private SupplierSite supplierSite;

    @Transient
    private String taxRegistrationNumber;

    @Transient
    private String paymentMethodCode;

}
