package org.experteam.efatura.cloud.oracle.job;

import lombok.extern.slf4j.Slf4j;
import org.experteam.efatura.cloud.oracle.domain.OracleCloudInvoice;
import org.experteam.efatura.cloud.oracle.repository.mongodb.OracleCloudInvoiceRepository;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@DisallowConcurrentExecution
public class SendUnprocessedInvoicesToCloudJob extends SendInvoiceToCloudJob {

    @Autowired
    private OracleCloudInvoiceRepository mongoRepository;

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        log.info("Invoices found with findByProcessedIsFalse():");
        log.info("-------------------------------");
        for (OracleCloudInvoice invoice : mongoRepository.findByProcessedIsFalse()) {
            super.sendInvoice(invoice);
        }
    }
}
