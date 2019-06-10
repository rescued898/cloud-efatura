package org.experteam.efatura.service;

import org.experteam.efatura.domain.Invoice;

public interface CloudInvoiceService<T extends Invoice> {
    T sendInvoice(String endpointUrl, T invoice);
}
