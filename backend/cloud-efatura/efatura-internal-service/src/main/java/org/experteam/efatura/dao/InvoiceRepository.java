package org.experteam.efatura.dao;

import org.experteam.efatura.domain.BaseInvoice;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface InvoiceRepository extends MongoRepository<BaseInvoice, Long>, InvoiceRepositoryCustom {
    List<BaseInvoice> findByUuid(String uuid);
}
