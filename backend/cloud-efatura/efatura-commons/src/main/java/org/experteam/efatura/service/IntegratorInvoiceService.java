package org.experteam.efatura.service;

import org.experteam.efatura.domain.BaseInvoice;

import java.util.Date;
import java.util.List;


public interface IntegratorInvoiceService {

    List<BaseInvoice> getInvoices(String endpointUrl, String companyTaxCode, Date invoiceDate);
}
