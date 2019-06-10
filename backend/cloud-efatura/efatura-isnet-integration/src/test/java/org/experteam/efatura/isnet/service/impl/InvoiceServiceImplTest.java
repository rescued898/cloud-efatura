package org.experteam.efatura.isnet.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.experteam.efatura.domain.BaseInvoice;
import org.experteam.efatura.isnet.config.IsnetBeanConfig;
import org.experteam.efatura.isnet.util.TestUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = IsnetBeanConfig.class)
public class InvoiceServiceImplTest {
    @Autowired
    private InvoiceServiceImpl invoiceService;

    @Test
    public void getInvoices() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        Date invoiceDate = sdf.parse("22-03-2019");

        List<BaseInvoice> baseInvoiceList =
                invoiceService.getInvoices(TestUtils.ISBANK_ENTEGRATOR_INVOICE_SERVICE_ENDPOINT, TestUtils.ISBANK_TEST_VERGI_KODU, invoiceDate);


        assertNotNull(baseInvoiceList);
        assertTrue(baseInvoiceList.size() > 0);

        for (BaseInvoice baseInvoice : baseInvoiceList) {
            assertNotNull(baseInvoice.getInvoiceNumber());
            assertNotNull(baseInvoice.getUuid());
            assertNotNull(baseInvoice.getInvoiceLines());
            assertNotNull(baseInvoice.getLineCountNumeric());
            assertTrue(baseInvoice.getInvoiceLines().size() > 0);
            assertEquals(baseInvoice.getLineCountNumeric().intValue(), baseInvoice.getInvoiceLines().size());
        }

    }
}