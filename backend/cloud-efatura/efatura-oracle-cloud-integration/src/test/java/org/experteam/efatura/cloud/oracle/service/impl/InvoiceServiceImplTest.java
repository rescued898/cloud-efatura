package org.experteam.efatura.cloud.oracle.service.impl;

import com.oracle.xmlns.apps.financials.payables.invoices.quickinvoices.invoiceinterfaceservice.InvoiceInterfaceHeader;
import lombok.extern.slf4j.Slf4j;
import org.experteam.efatura.cloud.oracle.config.OracleCloudBeanConfig;
import org.experteam.efatura.cloud.oracle.domain.OracleCloudInvoice;
import org.experteam.efatura.cloud.oracle.domain.OracleCloudInvoiceLine;
import org.experteam.efatura.cloud.oracle.util.TestUtils;
import org.experteam.efatura.domain.Amount;
import org.experteam.efatura.domain.Identification;
import org.experteam.efatura.domain.InvoiceDirection;
import org.experteam.efatura.domain.Party;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import static org.junit.Assert.assertNotNull;

@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = OracleCloudBeanConfig.class)
@TestPropertySource(properties = {
        "client.invoice-service.default-uri=https://ejwy-test.fa.em2.oraclecloud.com:443/fscmService/InvoiceInterfaceService",
        "client.user.name=exp.socal",
        "client.user.password=Welcome1",
})
public class InvoiceServiceImplTest {
    @Autowired
    private InvoiceServiceImpl invoiceService;

    @Test
    public void sendInvoice() {
        OracleCloudInvoice invoice = invoiceService.sendInvoice(TestUtils.ISBANK_ORACLE_CLOUD_INVOICE_SERVICE_WSDL, createDummyInvoice());

        getInvoice(invoice.getInvoiceId(), invoice.getLoadRequestId());

        log.info(invoice.getInvoiceNumber() + " invoice created on interface.");

        // invoiceService.submitInvoiceImportJob(TestUtils.ISBANK_ORACLE_CLOUD_INVOICE_SERVICE_WSDL, );
    }

    public void getInvoice(long invoiceId, long loadRequestId) {
        InvoiceInterfaceHeader interfaceHeader =
                invoiceService.getInvoice(TestUtils.ISBANK_ORACLE_CLOUD_INVOICE_SERVICE_WSDL, invoiceId, loadRequestId);

        assertNotNull(interfaceHeader);
    }

    private String getRandomInvoiceNumber() {
        // It will generate 6 digit random Number.
        // from 0 to 999999
        Random rnd = new Random();
        int number = rnd.nextInt(999999);

        // this will convert any number sequence into 6 character.
        return String.format("%06d", number);
    }

    private OracleCloudInvoice createDummyInvoice() {
        OracleCloudInvoice dummyInvoice = new OracleCloudInvoice();
        dummyInvoice.setInvoiceNumber(getRandomInvoiceNumber());
        dummyInvoice.setProfileId("TEMELFATURA");
        dummyInvoice.setIssueDate("09-04-2019");
        dummyInvoice.setInvoiceTypeCode("SATIS");
        dummyInvoice.getNote().add("oracle cloud test faturasi");
        dummyInvoice.setDocumentCurrencyCode("TRY");
        dummyInvoice.setDirection(InvoiceDirection.IN);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String issueDate = sdf.format(new Date());
        dummyInvoice.setIssueDate(issueDate);

        Party supplierParty = new Party();
        Identification sPid = new Identification();
        sPid.setSchemeID("VKN");
        sPid.setValue("4810173324");
        supplierParty.getPartyIdentification().add(sPid);
        dummyInvoice.setAccountingSupplierParty(supplierParty);

        Party customerParty = new Party();
        Identification cPid = new Identification();
        cPid.setSchemeID("VKN");
        cPid.setValue("4810058590");
        customerParty.getPartyIdentification().add(cPid);
        dummyInvoice.setAccountingCustomerParty(customerParty);

        dummyInvoice.setPayableAmount(createDummyAmount());

        OracleCloudInvoiceLine dummyInvoiceLine = new OracleCloudInvoiceLine();
        dummyInvoiceLine.setId("1");
        dummyInvoiceLine.setLineExtensionAmount(createDummyAmount());

        dummyInvoice.getInvoiceLines().add(dummyInvoiceLine);

        return dummyInvoice;
    }

    private Amount createDummyAmount() {
        Amount amount = new Amount();
        amount.setAmount(BigDecimal.valueOf(1000));
        amount.setCurrencyCode("TRY");
        return amount;
    }

}