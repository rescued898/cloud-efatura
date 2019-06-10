package org.experteam.efatura.dao;

import org.experteam.efatura.domain.BaseInvoice;

import java.util.List;

public interface InvoiceRepositoryCustom {
    List<BaseInvoice> query(SearchQuery searchQuery);
}
