package org.experteam.efatura.cloud.oracle.domain;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.math.BigDecimal;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class EfaturaConfig {

    @JsonAlias("Source_c")
    private String source;

    @JsonAlias("OrgId_c")
    private Long orgId;

    @JsonAlias("OperatingUnit_c")
    private String operatingUnit;

    @JsonAlias("AddTaxToInvoiceAmountFlag_c")
    private Boolean addTaxToInvoiceAmountFlag;

    @JsonAlias("ApplicationId_c")
    private long applicationId;

    @JsonAlias("AmountApplicableToDiscount_c")
    private BigDecimal amountApplicableToDiscount;

    @JsonAlias("ApplyAdvancesFlag_c")
    private Boolean applyAdvancesFlag;

    @JsonAlias("DefaultCreatedBy_c")
    private String createdBy;

    @JsonAlias("CalculateTaxDuringImportFlag_c")
    private Boolean calculateTaxDuringImportFlag;

    @JsonAlias("ExchangeRateType_c")
    private String exchangeRateType;

    @JsonAlias("ExclusivePaymentFlag_c")
    private Boolean exclusivePaymentFlag;

    @JsonAlias("IncludesPrepayFlag_c")
    private Boolean includesPrepayFlag;

    @JsonAlias("InvoiceTypeLookupCode_c")
    private String invoiceTypeLookupCode;

    // private long legalEntityId;

    @JsonAlias("LegalEntityName_c")
    private String legalEntityName;

    @JsonAlias("NetOfRetainageFlag_c")
    private Boolean netOfRetainageFlag;

    @JsonAlias("TaxationCountry_c")
    private String taxationCountry;
}
