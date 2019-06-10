package org.experteam.efatura.cloud.oracle.domain.adapter;

import com.oracle.xmlns.adf.svc.types.AmountType;
import com.oracle.xmlns.apps.financials.payables.invoices.quickinvoices.invoiceinterfaceservice.InvoiceInterfaceHeader;
import com.oracle.xmlns.apps.financials.payables.invoices.quickinvoices.invoiceinterfaceservice.InvoiceInterfaceLine;
import com.oracle.xmlns.apps.financials.payables.invoices.quickinvoices.invoiceinterfaceservice.ObjectFactory;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.MappingContext;
import ma.glasnost.orika.converter.ConverterFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.apache.commons.lang3.StringUtils;
import org.experteam.efatura.cloud.oracle.domain.OracleCloudInvoice;
import org.experteam.efatura.cloud.oracle.domain.OracleCloudInvoiceLine;
import org.experteam.efatura.cloud.oracle.domain.Supplier;
import org.experteam.efatura.cloud.oracle.util.DateUtils;
import org.experteam.efatura.domain.converter.BigDecimalToIntegerConverter;
import org.experteam.efatura.domain.converter.LongToBigDecimalConverter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Slf4j
public class InvoiceMapAdapter {
    public static MapperFacade createMapper() {
        MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();

        ConverterFactory converterFactory = mapperFactory.getConverterFactory();
        converterFactory.registerConverter("bigDecimalToIntConverter", new BigDecimalToIntegerConverter());
        converterFactory.registerConverter("longToBigDecimalConverter", new LongToBigDecimalConverter());

        mapperFactory = bindInvoice(mapperFactory);

        return mapperFactory.getMapperFacade();
    }

    static MapperFactory bindInvoice(MapperFactory mapperFactory) {
        mapperFactory.classMap(OracleCloudInvoice.class, InvoiceInterfaceHeader.class)
                .field("invoiceNumber", "invoiceNumber")
                //.fieldMap("orgId", "orgId").converter("longToBigDecimalConverter").add()
                .field("supplier.supplierId", "vendorId")
                .field("supplier.supplierSite.supplierSiteId", "vendorSiteId")
                .field("payableAmount.amount", "invoiceAmount.value")
                .field("documentCurrencyCode", "invoiceAmount.currencyCode")
                .customize(
                        new CustomMapper<OracleCloudInvoice, InvoiceInterfaceHeader>() {
                            @Override
                            public void mapAtoB(OracleCloudInvoice invoice, InvoiceInterfaceHeader invoiceInterfaceHeader, MappingContext context) {
                                ObjectFactory factory = new ObjectFactory();

                                if (invoice.getEfaturaConfig() != null) {
                                    invoiceInterfaceHeader.setSource(factory.createInvoiceInterfaceHeaderSource(invoice.getEfaturaConfig().getSource()));
                                    invoiceInterfaceHeader.setOrgId(BigDecimal.valueOf(invoice.getEfaturaConfig().getOrgId()));
                                    invoiceInterfaceHeader.setOperatingUnit(factory.createInvoiceInterfaceHeaderOperatingUnit(
                                            invoice.getEfaturaConfig().getOperatingUnit()));
                                    invoiceInterfaceHeader.setAddTaxToInvoiceAmountFlag(
                                            factory.createInvoiceInterfaceHeaderAddTaxToInvoiceAmountFlag(
                                                    invoice.getEfaturaConfig().getAddTaxToInvoiceAmountFlag()));
                                    invoiceInterfaceHeader.setApplicationId(factory.createInvoiceInterfaceHeaderApplicationId(
                                            invoice.getEfaturaConfig().getApplicationId()));
                                    invoiceInterfaceHeader.setAmountApplicableToDiscount(
                                            factory.createInvoiceInterfaceHeaderAmountApplicableToDiscount(
                                                    invoice.getEfaturaConfig().getAmountApplicableToDiscount()));
                                    invoiceInterfaceHeader.setApplyAdvancesFlag(factory.createInvoiceInterfaceHeaderApplyAdvancesFlag(
                                            invoice.getEfaturaConfig().getApplyAdvancesFlag()));
                                    invoiceInterfaceHeader.setAttribute8(factory.createInvoiceInterfaceHeaderAttribute8(
                                            invoice.getEfaturaConfig().getCreatedBy()));

                                    invoiceInterfaceHeader.setCalculateTaxDuringImportFlag(
                                            factory.createInvoiceInterfaceHeaderCalculateTaxDuringImportFlag(
                                                    invoice.getEfaturaConfig().getCalculateTaxDuringImportFlag()));

                                    invoiceInterfaceHeader.setTaxationCountry(
                                            factory.createInvoiceInterfaceHeaderTaxationCountry(
                                                    invoice.getEfaturaConfig().getTaxationCountry()));

                                    invoiceInterfaceHeader.setExchangeRateType(
                                            factory.createInvoiceInterfaceHeaderExchangeRateType(invoice.getEfaturaConfig().getExchangeRateType()));
                                    invoiceInterfaceHeader.setExclusivePaymentFlag(
                                            factory.createInvoiceInterfaceHeaderExclusivePaymentFlag(invoice.getEfaturaConfig().getExclusivePaymentFlag()));

                                    invoiceInterfaceHeader.setInvoiceIncludesPrepayFlag(
                                            factory.createInvoiceInterfaceHeaderInvoiceIncludesPrepayFlag(invoice.getEfaturaConfig().getIncludesPrepayFlag()));
                                    invoiceInterfaceHeader.setInvoiceTypeLookupCode(
                                            factory.createInvoiceInterfaceHeaderInvoiceTypeLookupCode(invoice.getEfaturaConfig().getInvoiceTypeLookupCode()));
                                    // invoiceInterfaceHeader.setLegalEntityId(factory.createInvoiceInterfaceHeaderLegalEntityId(invoice.getLegalEntityId()));
                                    invoiceInterfaceHeader.setLegalEntityName(
                                            factory.createInvoiceInterfaceHeaderLegalEntityName(invoice.getEfaturaConfig().getLegalEntityName()));
                                    invoiceInterfaceHeader.setNetOfRetainageFlag(
                                            factory.createInvoiceInterfaceHeaderNetOfRetainageFlag(invoice.getEfaturaConfig().getNetOfRetainageFlag()));
                                }


                                if (invoice.getSupplier() != null) {
                                    Supplier supplier = invoice.getSupplier();

                                    invoiceInterfaceHeader.setVendorName(factory.createInvoiceInterfaceHeaderVendorName(supplier.getSupplierName()));
                                    invoiceInterfaceHeader.setVendorNumber(factory.createInvoiceInterfaceHeaderVendorNumber(supplier.getSupplierNumber()));
                                    invoiceInterfaceHeader.setVendorSiteCode(factory.createInvoiceInterfaceHeaderVendorSiteCode(supplier.getSupplierSite().getSupplierSiteCode()));
                                    invoiceInterfaceHeader.setPaymentMethodCode(factory.createInvoiceInterfaceHeaderPaymentMethodCode(supplier.getPaymentMethodCode()));

                                    /* invoiceInterfaceHeader.setTaxationCountry(
                                            factory.createInvoiceInterfaceHeaderTaxationCountry(supplier.getTaxationCountry())); */

                                    invoiceInterfaceHeader.setPartyId(factory.createInvoiceInterfaceHeaderPartyId(supplier.getPartyId()));
                                    invoiceInterfaceHeader.setPartySiteId(factory.createInvoiceInterfaceHeaderPartySiteId(supplier.getPartySiteId()));
                                }


                                invoiceInterfaceHeader.setAttribute9(factory.createInvoiceInterfaceHeaderAttribute9(invoice.getProfileId()));

                                for (String note : invoice.getNote()) {
                                    invoiceInterfaceHeader.setDescription(
                                            factory.createInvoiceInterfaceHeaderDescription(StringUtils.substring(note, 0, 240))
                                    );

                                    break;
                                }


                                LocalDate issueDate = LocalDate.parse(invoice.getIssueDate());

                                invoiceInterfaceHeader.setExchangeDate(factory.createInvoiceInterfaceHeaderExchangeDate(DateUtils.toXmlCalendar(issueDate)));

                                LocalDate sysdate = LocalDate.now();

                                invoiceInterfaceHeader.setGlDate(factory.createInvoiceInterfaceHeaderGlDate(DateUtils.toXmlCalendar(sysdate)));
                                invoiceInterfaceHeader.setImportDocumentDate(factory.createInvoiceInterfaceHeaderImportDocumentDate(DateUtils.toXmlCalendar(issueDate)));

                                /*AmountType invAmountType = new AmountType();
                                invAmountType.setCurrencyCode(invoice.getDocumentCurrencyCode());
                                invAmountType.setValue(invoice.getPayableAmount().getAmount());
                                invoiceInterfaceHeader.setInvoiceAmount(invAmountType);*/

                                invoiceInterfaceHeader.setInvoiceCurrencyCode(
                                        factory.createInvoiceInterfaceHeaderInvoiceCurrencyCode(invoice.getDocumentCurrencyCode()));
                                invoiceInterfaceHeader.setInvoiceDate(factory.createInvoiceInterfaceHeaderInvoiceDate(DateUtils.toXmlCalendar(issueDate)));
                                invoiceInterfaceHeader.setPaymentCurrencyCode(
                                        factory.createInvoiceInterfaceHeaderPaymentCurrencyCode(invoice.getDocumentCurrencyCode()));

                                invoiceInterfaceHeader.setBudgetDate(factory.createInvoiceInterfaceHeaderBudgetDate(DateUtils.toXmlCalendar(sysdate)));

                                /*
                                switch (invoice.getDirection()) {
                                    case IN:

                                        for (Identification pid : invoice.getAccountingCustomerParty().getPartyIdentification()) {
                                            if (StringUtils.equalsIgnoreCase(pid.getSchemeID(), "VKN")) {
                                                invoiceInterfaceHeader.setFirstPartyRegistrationNumber(
                                                        factory.createInvoiceInterfaceHeaderFirstPartyRegistrationNumber(pid.getValue()));

                                                break;
                                            }
                                        }

                                        for (Identification pid : invoice.getAccountingSupplierParty().getPartyIdentification()) {
                                            if (StringUtils.equalsIgnoreCase(pid.getSchemeID(), "VKN")) {
                                                invoiceInterfaceHeader.setThirdPartyRegistrationNumber(
                                                        factory.createInvoiceInterfaceHeaderThirdPartyRegistrationNumber(pid.getValue()));

                                                break;
                                            }
                                        }

                                        break;
                                }*/


                                for (OracleCloudInvoiceLine ocInvoiceLine : invoice.getInvoiceLines()) {
                                    InvoiceInterfaceLine invoiceInterfaceLine = factory.createInvoiceInterfaceLine();

                                    invoiceInterfaceLine.setAccountingDate(factory.createInvoiceInterfaceLineAccountingDate(DateUtils.toXmlCalendar(sysdate)));

                                    AmountType lineAmountType = new AmountType();
                                    lineAmountType.setValue(ocInvoiceLine.getLineExtensionAmount().getAmount());
                                    lineAmountType.setCurrencyCode(invoice.getDocumentCurrencyCode());
                                    invoiceInterfaceLine.setAmount(lineAmountType);

                                    invoiceInterfaceLine.setAssetsTrackingFlag(factory.createInvoiceInterfaceLineAssetsTrackingFlag(ocInvoiceLine.getAssetsTrackingFlag()));
                                    invoiceInterfaceLine.setFinalMatchFlag(factory.createInvoiceInterfaceLineFinalMatchFlag(ocInvoiceLine.getFinalMatchFlag()));
                                    invoiceInterfaceLine.setLineNumber(factory.createInvoiceInterfaceLineLineNumber(Integer.valueOf(ocInvoiceLine.getId())));
                                    invoiceInterfaceLine.setLineTypeLookupCode(ocInvoiceLine.getLineLookupCode());
                                    invoiceInterfaceLine.setBudgetDate(factory.createInvoiceInterfaceLineBudgetDate(DateUtils.toXmlCalendar(sysdate)));


                                    if (invoice.getEfaturaConfig() != null) {
                                        invoiceInterfaceLine.setOrgId(factory.createInvoiceInterfaceLineOrgId(invoice.getEfaturaConfig().getOrgId()));
                                        invoiceInterfaceLine.setApplicationId(factory.createInvoiceInterfaceLineApplicationId(invoice.getEfaturaConfig().getApplicationId()));
                                    }

                                    invoiceInterfaceHeader.getInvoiceInterfaceLine().add(invoiceInterfaceLine);
                                }

                            }
                        }
                )
                .register();

        return mapperFactory;
    }
}
