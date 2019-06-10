package org.experteam.efatura.isnet.repository;

import org.experteam.efatura.domain.BaseInvoice;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface InvoiceRepository extends MongoRepository<BaseInvoice, Long> {
}
