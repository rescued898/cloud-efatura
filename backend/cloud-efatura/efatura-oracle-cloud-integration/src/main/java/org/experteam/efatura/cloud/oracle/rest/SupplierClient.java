package org.experteam.efatura.cloud.oracle.rest;

import lombok.extern.slf4j.Slf4j;
import org.experteam.efatura.cloud.oracle.domain.Supplier;
import org.experteam.efatura.cloud.oracle.domain.Suppliers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class SupplierClient {
    private final String suppliersPath = "/fscmRestApi/resources/11.13.18.05/suppliers";

    @Autowired
    private RestTemplate restTemplate;

    public Supplier getSupplierByTaxRegNumber(String host, String taxRegNumber) throws RestClientException {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "application/json");

        UriComponents uriComponents = UriComponentsBuilder.newInstance()
                .scheme("https").host(host)
                .path(suppliersPath)
                .query("q=TaxRegistrationNumber={taxRegNumber}").buildAndExpand(taxRegNumber);

        HttpEntity entity = new HttpEntity(headers);

        log.info("calling suppliers REST API endpoint  " + uriComponents.toUriString());

        HttpEntity<Suppliers> response = restTemplate.exchange(
                uriComponents.toUriString(),
                HttpMethod.GET,
                entity,
                Suppliers.class);

        if (response.getBody().getItems().size() < 1) {
            throw new RestClientException(taxRegNumber + " VKN için tedarikci bulunamadi.");
        }

        List<Supplier> suppliers = response.getBody().getItems().stream()
                .distinct()
                .collect(Collectors.toList());

        if (suppliers.size() > 1) {
            for (Supplier supplier : suppliers)
                log.error(supplier.toString());

            throw new RestClientException(taxRegNumber + " VKN için birden fazla tedarikci bulundu. Logu kontrol ediniz.");
        }


        Supplier supplier = suppliers.get(0);
        supplier.setTaxRegistrationNumber(taxRegNumber);

        return supplier;
    }
}
