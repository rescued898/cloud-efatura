package org.experteam.efatura.isnet;

import lombok.extern.slf4j.Slf4j;
import org.experteam.efatura.domain.BaseInvoice;
import org.experteam.efatura.domain.IntgStatus;
import org.experteam.efatura.domain.InvoiceDirection;
import org.experteam.efatura.isnet.repository.InvoiceRepository;
import org.experteam.efatura.isnet.service.impl.InvoiceServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Slf4j
@SpringBootApplication//(exclude={DataSourceAutoConfiguration.class})
@EnableMongoRepositories({"org.experteam.efatura.isnet.repository"})
public class IsnetIntegratorApplication implements CommandLineRunner {

    @Autowired
    private InvoiceServiceImpl invoiceService;

    @Autowired
    private InvoiceRepository mongoRepository;

    public static void main(String[] args) {
        ConfigurableApplicationContext ctx = SpringApplication.run(IsnetIntegratorApplication.class, args);

        ctx.close();
    }

    @Override
    public void run(String... args) throws Exception {
        // mongoRepository.deleteAll();

        String companyTaxCode = "1234567805";
        String endpointUrl = "http://einvoiceservicetest.isnet.net.tr/InvoiceService/ServiceContract/InvoiceService.svc";

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        Date invoiceDate = sdf.parse("29-03-2019");

        List<BaseInvoice> baseInvoices = invoiceService.getInvoices(endpointUrl, companyTaxCode, invoiceDate);
        for (BaseInvoice invoice : baseInvoices) {
            invoice.setDirection(InvoiceDirection.IN);
            invoice.setIntegrationStatus(IntgStatus.ON_INTERFACE);

            log.info("Invoice ETTN: " + invoice.getUuid());
            mongoRepository.save(invoice);
        }

        /*log.info("Invoices found with findAll():");
        log.info("-------------------------------");
        for (BaseInvoice invoice : mongoRepository.findAll()) {
            log.info(invoice.toString());
        }*/
    }

}
