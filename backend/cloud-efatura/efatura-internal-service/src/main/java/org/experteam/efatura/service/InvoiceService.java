package org.experteam.efatura.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.experteam.efatura.dao.InvoiceRepository;
import org.experteam.efatura.dao.SearchQuery;
import org.experteam.efatura.domain.BaseInvoice;
import org.experteam.efatura.graphql.pagination.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Transactional
@Service
public class InvoiceService {

    @Autowired
    private InvoiceRepository invoiceRepository;

    public List<BaseInvoice> findAllInvoices() {
        return invoiceRepository.findAll();
    }

    public Page<BaseInvoice> findInvoices(int pageNumber, int pageSize, SortOrder sortOrder, String sortBy) {
        final Sort sort = Sort.by(Sort.Direction.fromString(sortOrder.name()), sortBy);
        Pageable paging = PageRequest.of(pageNumber, pageSize, sort);
        return invoiceRepository.findAll(paging);
    }

    public List<BaseInvoice> searchInvoices(String direction, String filter) {
        SearchQuery searchQuery = new SearchQuery();
        searchQuery.setDirection(direction);
        if (StringUtils.isNotEmpty(filter)) {
            searchQuery.setCustomerPartyNameLike(filter);
            searchQuery.setSupplierPartyNameLike(filter);
            searchQuery.setInvoiceNumberLike(filter);
        }

        return invoiceRepository.query(searchQuery);
    }
}
