package org.experteam.efatura.job;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.experteam.efatura.cloud.oracle.domain.*;
import org.experteam.efatura.cloud.oracle.rest.EfaturaConfigClient;
import org.experteam.efatura.cloud.oracle.rest.SupplierClient;
import org.experteam.efatura.cloud.oracle.rest.SupplierSiteClient;
import org.experteam.efatura.cloud.oracle.service.impl.InvoiceServiceImpl;
import org.experteam.efatura.cloud.oracle.service.impl.ReportServiceImpl;
import org.experteam.efatura.domain.Identification;
import org.experteam.efatura.domain.IntgStatus;
import org.experteam.efatura.mongodb.OracleCloudInvoiceRepository;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@DisallowConcurrentExecution
public class SendInvoiceToCloudJob extends QuartzJobBean {

    public static final String UUID = "uuid";

    @Autowired
    private InvoiceServiceImpl cloudManager;

    @Autowired
    private ReportServiceImpl reportService;

    @Autowired
    private OracleCloudInvoiceRepository mongoRepository;

    @Autowired
    private SupplierClient supplierClient;

    @Autowired
    private SupplierSiteClient supplierSiteClient;

    @Autowired
    private EfaturaConfigClient efaturaConfigClient;

    @Value("${client.host-name}")
    private String hostName;

    @Value("${client.invoice-service.default-uri}")
    private String invoiceServiceEndpointUrl;

    @Value("${client.report-service.default-uri}")
    private String reportServiceEndpointUrl;

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        JobDataMap dataMap = context.getJobDetail().getJobDataMap();
        //fetch parameters from JobDataMap
        final String uuid = dataMap.getString(UUID);

        log.info("UUID: " + uuid);

        OracleCloudInvoice invoice = mongoRepository.findByUuid(uuid);

        if (invoice != null) {
            if (invoice.isProcessed()) {
                log.warn("Invoice already processed.");
            } else {
                sendInvoice(invoice);
            }
        } else {
            log.error("Invoice could not be found!");
        }

    }

    protected void sendInvoice(OracleCloudInvoice invoice) {
        log.info("Invoice number: " + invoice.getInvoiceNumber());

        String taxRegNumber = "";
        for (Identification pid : invoice.getAccountingSupplierParty().getPartyIdentification()) {
            if (StringUtils.equalsIgnoreCase(pid.getSchemeID(), "VKN")) {
                taxRegNumber = pid.getValue();

                break;
            }
        }

        log.info("Supplier tax registration number: " + taxRegNumber);

        EfaturaConfig efaturaConfig = efaturaConfigClient.getEfaturaConfig(hostName);

        invoice.setEfaturaConfig(efaturaConfig);

        Supplier supplier = supplierClient.getSupplierByTaxRegNumber(hostName, taxRegNumber);

        List<SupplierSite> supplierSites = supplierSiteClient.getSupplierSites(hostName, supplier.getSupplierId());

        supplier.setSupplierSite(supplierSites.get(0));

        SupplierDetailItem supplierDetailItem =
                reportService.getSupplierDetail(reportServiceEndpointUrl, "" + supplier.getSupplierId(), invoice.getDocumentCurrencyCode());

        supplier.setPartyId(supplierDetailItem.getPartyId());
        supplier.setPartySiteId(supplierDetailItem.getPartySiteId());
        supplier.setPaymentMethodCode(supplierDetailItem.getPaymentMethod());

        invoice.setSupplier(supplier);

        log.info("Supplier: " + supplier.toString());

        cloudManager.sendInvoice(invoiceServiceEndpointUrl, invoice);

        log.info("Invoice with additional informations sent to cloud.");

        invoice.setProcessed(true);
        invoice.setIntegrationStatus(IntgStatus.ON_CLOUD);

        mongoRepository.save(invoice);

        log.info("Invoice updated as processed.");
    }
}
