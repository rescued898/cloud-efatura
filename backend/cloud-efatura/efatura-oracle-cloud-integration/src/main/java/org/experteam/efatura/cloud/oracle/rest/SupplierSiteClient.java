package org.experteam.efatura.cloud.oracle.rest;

import lombok.extern.slf4j.Slf4j;
import org.experteam.efatura.cloud.oracle.domain.SupplierSite;
import org.experteam.efatura.cloud.oracle.domain.SupplierSites;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@Slf4j
public class SupplierSiteClient {
    private final String suppliersPath = "/fscmRestApi/resources/11.13.18.05/suppliers";

    @Autowired
    private RestTemplate restTemplate;

    public List<SupplierSite> getSupplierSites(String host, Long supplierId) throws RestClientException {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "application/json");

        UriComponents uriComponents = UriComponentsBuilder.newInstance()
                .scheme("https").host(host)
                .path(suppliersPath + "/" + supplierId + "/child/sites").build();

        HttpEntity entity = new HttpEntity(headers);

        log.info("calling suppliers REST API endpoint  " + uriComponents.toUriString());

        HttpEntity<SupplierSites> response = restTemplate.exchange(
                uriComponents.toUriString(),
                HttpMethod.GET,
                entity,
                SupplierSites.class);

        if (response.getBody().getItems().size() < 1) {
            throw new RestClientException(supplierId + " degeri için tedarikci site bulunamadi.");
        }

        return response.getBody().getItems();
    }
}
