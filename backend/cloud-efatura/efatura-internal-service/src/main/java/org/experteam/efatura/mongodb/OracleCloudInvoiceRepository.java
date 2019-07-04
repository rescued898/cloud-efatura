package org.experteam.efatura.mongodb;

import org.experteam.efatura.cloud.oracle.domain.OracleCloudInvoice;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface OracleCloudInvoiceRepository extends MongoRepository<OracleCloudInvoice, Long> {
    OracleCloudInvoice findByInvoiceNumber(String invoiceNumber);

    List<OracleCloudInvoice> findByProcessedIsFalse();

    OracleCloudInvoice findByUuid(String uuid);
}
