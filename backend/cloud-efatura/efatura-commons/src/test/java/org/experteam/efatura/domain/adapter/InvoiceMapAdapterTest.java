package org.experteam.efatura.domain.adapter;

import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import oasis.names.specification.ubl.schema.xsd.invoice_2.InvoiceType;
import org.experteam.efatura.domain.BaseInvoice;
import org.junit.Before;
import org.junit.Test;

import javax.xml.bind.JAXB;
import java.io.File;
import java.io.StringReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@Slf4j
public class InvoiceMapAdapterTest {
    private MapperFacade invoiceMapper;

    @Before
    public void setUp() {
        invoiceMapper = InvoiceMapAdapter.createMapper();
    }

    @Test
    public void bindInvoice() throws Exception {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("./xml/invoice-ATR20190000000218.xml").getFile());
        System.out.println(file.getAbsolutePath());

        Path xmlPath = Paths.get(getClass().getClassLoader()
                .getResource("./xml/invoice-ATR20190000000218.xml").toURI());

        String invoiceXmlStr = new String(Files.readAllBytes(xmlPath));

        assertNotNull(invoiceXmlStr);
        log.info("invoice xml: \n" + invoiceXmlStr);
        log.info("===============================================================================");

        InvoiceType invoiceType = JAXB.unmarshal(new StringReader(invoiceXmlStr), InvoiceType.class);

        assertNotNull(invoiceType);
        assertEquals(invoiceType.getID().getValue(), "ATR2019000000218");
        assertEquals(invoiceType.getInvoiceLine().size(), 1);

        BaseInvoice baseInvoice = invoiceMapper.map(invoiceType, BaseInvoice.class);

        assertNotNull(baseInvoice);
        assertEquals(baseInvoice.getInvoiceNumber(), invoiceType.getID().getValue());
        assertEquals(baseInvoice.getInvoiceLines().size(), baseInvoice.getInvoiceLines().size());

        log.info("baseInvoice: \n" + baseInvoice.toString());
    }
}