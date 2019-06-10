package org.experteam.efatura.dao;

import lombok.Data;

@Data
public class SearchQuery {
    private String direction;
    private String invoiceNumberLike;
    private String customerPartyNameLike;
    private String supplierPartyNameLike;
}
