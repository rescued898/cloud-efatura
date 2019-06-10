package org.experteam.efatura.graphql.resolvers;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import org.experteam.efatura.domain.BaseInvoice;
import org.experteam.efatura.graphql.pagination.SortOrder;
import org.experteam.efatura.service.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Query implements GraphQLQueryResolver {

    @Autowired
    private InvoiceService invoiceService;

    public List<BaseInvoice> findAllInvoices() {
        return invoiceService.findAllInvoices();
    }

    public Page<BaseInvoice> findInvoices(int pageNumber, int pageSize, SortOrder sortOrder, String sortBy) {
        return invoiceService.findInvoices(pageNumber, pageSize, sortOrder, sortBy);
    }

    public List<BaseInvoice> searchInvoices(String direction, String filter) {
        return invoiceService.searchInvoices(direction, filter);
    }

    public long invoiceCount() {
        return invoiceService.findAllInvoices().size();
    }
}