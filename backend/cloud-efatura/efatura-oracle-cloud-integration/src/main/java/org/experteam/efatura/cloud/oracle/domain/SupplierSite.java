package org.experteam.efatura.cloud.oracle.domain;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class SupplierSite {

    @JsonAlias("SupplierSiteId")
    private Long supplierSiteId;

    @JsonAlias("SupplierSite")
    private String supplierSiteCode;
}
