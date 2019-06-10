package org.experteam.efatura.domain.adapter;

import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.MappingContext;
import ma.glasnost.orika.converter.ConverterFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_2.*;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.NoteType;
import oasis.names.specification.ubl.schema.xsd.invoice_2.InvoiceType;
import oasis.names.specification.ubl.schema.xsd.unqualifieddatatypes_2.AmountType;
import org.experteam.efatura.domain.*;
import org.experteam.efatura.domain.converter.BigDecimalToIntegerConverter;
import org.experteam.efatura.domain.converter.XmlGregorianCalendarToStringDateConverter;
import org.experteam.efatura.domain.converter.XmlGregorianCalendarToStringTimeConverter;
import un.unece.uncefact.data.specification.corecomponenttypeschemamodule._2.QuantityType;

public class InvoiceMapAdapter {
    public static MapperFacade createMapper() {
        MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();

        ConverterFactory converterFactory = mapperFactory.getConverterFactory();
        converterFactory.registerConverter("bigDecimalToIntConverter", new BigDecimalToIntegerConverter());
        converterFactory.registerConverter("xmlCalendarToStringDateConverter", new XmlGregorianCalendarToStringDateConverter());
        converterFactory.registerConverter("xmlCalendarToStringTimeConverter", new XmlGregorianCalendarToStringTimeConverter());

        mapperFactory = bindInvoice(mapperFactory);

        return mapperFactory.getMapperFacade();
    }

    static MapperFactory bindInvoice(MapperFactory mapperFactory) {
        mapperFactory.classMap(BaseInvoice.class, InvoiceType.class)
                .field("profileId", "profileID.value")
                .field("invoiceNumber", "ID.value")
                .field("copyIndicator", "copyIndicator.value")
                .field("uuid", "UUID.value")
                .fieldMap("issueDate", "issueDate.value").converter("xmlCalendarToStringDateConverter").add()
                .fieldMap("issueTime", "issueTime.value").converter("xmlCalendarToStringTimeConverter").add()
                .fieldMap("lineCountNumeric", "lineCountNumeric.value").converter("bigDecimalToIntConverter").add()
                .field("invoiceTypeCode", "invoiceTypeCode.value")
                .field("documentCurrencyCode", "documentCurrencyCode.value")
                .field("lineExtensionAmount.amount", "legalMonetaryTotal.lineExtensionAmount.value")
                .field("lineExtensionAmount.currencyCode", "legalMonetaryTotal.lineExtensionAmount.currencyID")
                .field("taxExclusiveAmount.amount", "legalMonetaryTotal.taxExclusiveAmount.value")
                .field("taxExclusiveAmount.currencyCode", "legalMonetaryTotal.taxExclusiveAmount.currencyID")
                .field("taxInclusiveAmount.amount", "legalMonetaryTotal.taxInclusiveAmount.value")
                .field("taxInclusiveAmount.currencyCode", "legalMonetaryTotal.taxInclusiveAmount.currencyID")
                .field("allowanceTotalAmount.amount", "legalMonetaryTotal.allowanceTotalAmount.value")
                .field("allowanceTotalAmount.currencyCode", "legalMonetaryTotal.allowanceTotalAmount.currencyID")
                .field("payableAmount.amount", "legalMonetaryTotal.payableAmount.value")
                .field("payableAmount.currencyCode", "legalMonetaryTotal.payableAmount.currencyID")
                .customize(
                        new CustomMapper<BaseInvoice, InvoiceType>() {
                            @Override
                            public void mapBtoA(InvoiceType invoiceType, BaseInvoice baseInvoice, MappingContext context) {
                                for (NoteType noteType : invoiceType.getNote()) {
                                    baseInvoice.getNote().add(noteType.getValue());
                                }

                                Party supplierParty = bindParty(invoiceType.getAccountingSupplierParty().getParty());
                                baseInvoice.setAccountingSupplierParty(supplierParty);

                                Party customerParty = bindParty(invoiceType.getAccountingCustomerParty().getParty());
                                baseInvoice.setAccountingCustomerParty(customerParty);

                                for (TaxTotalType taxTotalType : invoiceType.getTaxTotal())
                                    baseInvoice.getTaxTotals().add(bindTaxTotal(taxTotalType));

                                for (InvoiceLineType invoiceLineType : invoiceType.getInvoiceLine())
                                    baseInvoice.getInvoiceLines().add(bindLine(invoiceLineType));
                            }
                        }
                )
                .register();

        return mapperFactory;
    }

    private static BaseInvoiceLine bindLine(InvoiceLineType lineType) {
        BaseInvoiceLine invoiceLine = new BaseInvoiceLine();

        if (lineType.getID() != null)
            invoiceLine.setId(lineType.getID().getValue());

        if (lineType.getInvoicedQuantity() != null)
            invoiceLine.setInvoicedQuantity(bindQuantity(lineType.getInvoicedQuantity()));

        if (lineType.getLineExtensionAmount() != null)
            invoiceLine.setLineExtensionAmount(bindAmount(lineType.getLineExtensionAmount()));

        for (NoteType noteType : lineType.getNote())
            invoiceLine.getNote().add(noteType.getValue());

        for (TaxTotalType taxTotalType : lineType.getTaxTotal())
            invoiceLine.getTaxTotals().add(bindTaxTotal(taxTotalType));

        invoiceLine.setItem(bindItem(lineType.getItem(), lineType.getPrice()));

        return invoiceLine;
    }

    private static Item bindItem(ItemType itemType, PriceType priceType) {
        Item item = new Item();

        if (itemType.getName() != null)
            item.setName(itemType.getName().getValue());

        if (itemType.getSellersItemIdentification() != null && itemType.getSellersItemIdentification().getID() != null)
            item.setSellersId(itemType.getSellersItemIdentification().getID().getValue());

        if (priceType.getPriceAmount() != null)
            item.setPrice(bindAmount(priceType.getPriceAmount()));

        return item;
    }

    private static Quantity bindQuantity(QuantityType quantityType) {
        Quantity quantity = new Quantity();
        quantity.setUnitCode(quantityType.getUnitCode());
        quantity.setValue(quantityType.getValue());

        return quantity;
    }

    private static Amount bindAmount(AmountType amountType) {
        Amount amount = new Amount();
        amount.setAmount(amountType.getValue());
        amount.setCurrencyCode(amountType.getCurrencyID());

        return amount;
    }

    private static TaxTotal bindTaxTotal(TaxTotalType taxTotalType) {
        TaxTotal taxTotal = new TaxTotal();

        if (taxTotalType.getTaxAmount() != null) {
            taxTotal.setTaxAmount(bindAmount(taxTotalType.getTaxAmount()));
        }

        for (TaxSubtotalType taxSubtotalType : taxTotalType.getTaxSubtotal()) {
            TaxSubtotal taxSubtotal = new TaxSubtotal();

            if (taxSubtotalType.getPercent() != null) {
                taxSubtotal.setPercent(taxSubtotalType.getPercent().getValue());
            }

            if (taxSubtotalType.getTaxAmount() != null) {
                taxSubtotal.setTaxAmount(bindAmount(taxSubtotalType.getTaxAmount()));
            }

            if (taxSubtotalType.getTaxableAmount() != null) {
                taxSubtotal.setTaxableAmount(bindAmount(taxSubtotalType.getTaxableAmount()));
            }

            if (taxSubtotalType.getTaxCategory() != null && taxSubtotalType.getTaxCategory().getTaxScheme() != null) {
                TaxSchemeType taxSchemeType = taxSubtotalType.getTaxCategory().getTaxScheme();

                if (taxSchemeType.getTaxTypeCode() != null) {
                    taxSubtotal.setTaxTypeCode(taxSchemeType.getTaxTypeCode().getValue());
                }

                if (taxSchemeType.getName() != null) {
                    taxSubtotal.setTaxCategoryName(taxSchemeType.getName().getValue());
                }
            }

            taxTotal.getTaxSubtotal().add(taxSubtotal);
        }

        return taxTotal;
    }

    private static Party bindParty(PartyType partyType) {
        Party party = new Party();

        for (PartyIdentificationType partyIdentificationType : partyType.getPartyIdentification()) {
            Identification identification = new Identification();
            identification.setSchemeID(partyIdentificationType.getID().getSchemeID());
            identification.setValue(partyIdentificationType.getID().getValue());

            party.getPartyIdentification().add(identification);
        }

        for (PartyNameType partyNameType : partyType.getPartyName()) {
            if (partyNameType.getName() != null) {
                party.setPartyName(partyNameType.getName().getValue());
                break;
            }
        }

        if (partyType.getPostalAddress() != null) {
            AddressType postalAddressType = partyType.getPostalAddress();

            PostalAddress postalAddress = new PostalAddress();
            if (postalAddressType.getBlockName() != null)
                postalAddress.setBlockName(postalAddressType.getBlockName().getValue());

            if (postalAddressType.getBuildingName() != null)
                postalAddress.setBuildingName(postalAddressType.getBuildingName().getValue());

            if (postalAddressType.getBuildingNumber() != null)
                postalAddress.setBuildingNumber(postalAddressType.getBuildingNumber().getValue());

            if (postalAddressType.getCityName() != null)
                postalAddress.setCityName(postalAddressType.getCityName().getValue());

            if (postalAddressType.getCitySubdivisionName() != null)
                postalAddress.setCitySubdivisionName(postalAddressType.getCitySubdivisionName().getValue());

            if (postalAddressType.getCountry() != null && postalAddressType.getCountry().getName() != null)
                postalAddress.setCountry(postalAddressType.getCountry().getName().getValue());

            if (postalAddressType.getDistrict() != null)
                postalAddress.setDistrict(postalAddressType.getDistrict().getValue());

            if (postalAddressType.getStreetName() != null)
                postalAddress.setStreetName(postalAddressType.getStreetName().getValue());

            party.setPostalAddress(postalAddress);
        }

        for (PartyTaxSchemeType partyTaxSchemeType : partyType.getPartyTaxScheme()) {
            if (partyTaxSchemeType.getTaxScheme() != null && partyTaxSchemeType.getTaxScheme().getName() != null)
                party.getTaxSchemeName().add(partyTaxSchemeType.getTaxScheme().getName().getValue());
        }

        return party;
    }
}
