package org.experteam.efatura.cloud.oracle.service.impl;

import com.oracle.xmlns.apps.financials.payables.invoices.quickinvoices.invoiceinterfaceservice.InvoiceInterfaceHeader;
import com.oracle.xmlns.apps.financials.payables.invoices.quickinvoices.invoiceinterfaceservice.types.CreateInvoiceInterface;
import com.oracle.xmlns.apps.financials.payables.invoices.quickinvoices.invoiceinterfaceservice.types.CreateInvoiceInterfaceResponse;
import com.oracle.xmlns.apps.financials.payables.invoices.quickinvoices.invoiceinterfaceservice.types.GetInvoiceInterface;
import com.oracle.xmlns.apps.financials.payables.invoices.quickinvoices.invoiceinterfaceservice.types.GetInvoiceInterfaceResponse;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.experteam.efatura.cloud.oracle.domain.OracleCloudInvoice;
import org.experteam.efatura.cloud.oracle.domain.adapter.InvoiceMapAdapter;
import org.experteam.efatura.service.CloudInvoiceService;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.client.core.SoapActionCallback;

@Slf4j
public class InvoiceServiceImpl extends WebServiceGatewaySupport implements CloudInvoiceService<OracleCloudInvoice> {
    private MapperFacade oracleCloudMapper;

    public InvoiceServiceImpl() {
        this.oracleCloudMapper = InvoiceMapAdapter.createMapper();
    }

    public OracleCloudInvoice sendInvoice(String endpointUrl, OracleCloudInvoice invoice) {
        CreateInvoiceInterface createInvoice = new CreateInvoiceInterface();

        InvoiceInterfaceHeader invoiceInterfaceHeader = oracleCloudMapper.map(invoice, InvoiceInterfaceHeader.class);

        createInvoice.setInvoiceInterfaceHeader(invoiceInterfaceHeader);

        CreateInvoiceInterfaceResponse response = (CreateInvoiceInterfaceResponse) getWebServiceTemplate()
                .marshalSendAndReceive(endpointUrl, createInvoice,
                        new SoapActionCallback("http://xmlns.oracle.com/apps/financials/payables/invoices/quickInvoices/invoiceInterfaceService/createInvoiceInterface"));

        for (InvoiceInterfaceHeader inv : response.getResult().getValue()) {
            log.info(inv.getInvoiceNumber() + " invoice created on interface. InvoiceId: " + inv.getInvoiceId().getValue());

            invoice.setInvoiceId(inv.getInvoiceId().getValue());
            invoice.setLoadRequestId(inv.getLoadRequestId().getValue());
        }

        return invoice;
    }

    public InvoiceInterfaceHeader getInvoice(String endpointUrl, Long invoiceId, Long loadRequestId) {
        GetInvoiceInterface getInvoice = new GetInvoiceInterface();

        getInvoice.setInvoiceId(invoiceId);
        getInvoice.setLoadRequestId(loadRequestId);

        GetInvoiceInterfaceResponse response = (GetInvoiceInterfaceResponse) getWebServiceTemplate()
                .marshalSendAndReceive(endpointUrl, getInvoice,
                        new SoapActionCallback("http://xmlns.oracle.com/apps/financials/payables/invoices/quickInvoices/invoiceInterfaceService/getInvoiceInterface"));

        return response.getResult();
    }
}
