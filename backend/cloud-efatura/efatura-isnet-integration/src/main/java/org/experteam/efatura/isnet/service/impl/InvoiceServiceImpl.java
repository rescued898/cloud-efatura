package org.experteam.efatura.isnet.service.impl;

import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.datacontract.schemas._2004._07.einvoice_service.*;
import org.experteam.efatura.domain.BaseInvoice;
import org.experteam.efatura.domain.adapter.InvoiceMapAdapter;
import org.experteam.efatura.service.IntegratorInvoiceService;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.client.core.SoapActionCallback;
import org.tempuri.SearchInvoice;

import javax.xml.bind.JAXB;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Slf4j
public class InvoiceServiceImpl extends WebServiceGatewaySupport implements IntegratorInvoiceService {
    private MapperFacade invoiceMapper;

    public InvoiceServiceImpl() {
        this.invoiceMapper = InvoiceMapAdapter.createMapper();
    }

    public List<BaseInvoice> getInvoices(String endpointUrl, String companyTaxCode, Date invoiceDate) {
        List<BaseInvoice> baseInvoices = new ArrayList<>();

        SearchInvoice searchInvoice = new SearchInvoice();

        ObjectFactory objectFactory = new ObjectFactory();

        SearchInvoiceRequest request = objectFactory.createSearchInvoiceRequest();
        request.setInvoiceDirection(InvoiceDirectionType.INCOMING);
        request.setCompanyTaxCode(companyTaxCode);

        InvoiceResultSet invoiceResultSet = objectFactory.createInvoiceResultSet();
        invoiceResultSet.setIsXMLIncluded(true);

        request.setResultSet(invoiceResultSet);

        Calendar invoiceDateCal = Calendar.getInstance();
        invoiceDateCal.setTime(invoiceDate);

        request.setMinInvoiceDate(invoiceDateCal);
        request.setMaxInvoiceDate(invoiceDateCal);

        searchInvoice.setRequest(request);

        log.info("Requesting invoices for " + companyTaxCode);

        org.tempuri.SearchInvoiceResponse response = (org.tempuri.SearchInvoiceResponse) getWebServiceTemplate()
                .marshalSendAndReceive(endpointUrl, searchInvoice,
                        new SoapActionCallback("http://tempuri.org/IInvoiceService/SearchInvoice"));

        if (response.getSearchInvoiceResult().getInvoices() != null) {
            for (Invoice invoice : response.getSearchInvoiceResult().getInvoices().getInvoice()) {
                log.trace("invoice number: " + invoice.getInvoiceNumber());

                log.trace("invoice ubl xml: \n");
                log.trace(new String(invoice.getInvoiceXml()));

                log.trace("=============================================================");

                oasis.names.specification.ubl.schema.xsd.invoice_2.InvoiceType invoiceType =
                        JAXB.unmarshal(new StringReader(new String(invoice.getInvoiceXml())), oasis.names.specification.ubl.schema.xsd.invoice_2.InvoiceType.class);

                baseInvoices.add(invoiceMapper.map(invoiceType, BaseInvoice.class));

            }

        }

        return baseInvoices;
    }
}
