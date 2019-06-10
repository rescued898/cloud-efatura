package org.experteam.efatura.cloud.oracle.rest;

import lombok.extern.slf4j.Slf4j;
import org.experteam.efatura.cloud.oracle.config.RestTemplateConfig;
import org.experteam.efatura.cloud.oracle.domain.Supplier;
import org.experteam.efatura.cloud.oracle.domain.Suppliers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.support.BasicAuthenticationInterceptor;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import static org.junit.Assert.assertNotNull;

@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = RestTemplateConfig.class)
@TestPropertySource(properties = {
        "client.user.name=exp.mdizdar",
        "client.user.password=bursa1234",
})
public class SupplierClientTest {

    @Autowired
    private SupplierClient supplierClient;

    @Test
    public void getSupplier() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "application/json");

        UriComponents uriComponents = UriComponentsBuilder.newInstance()
                .scheme("https").host("ejwy-test.fa.em2.oraclecloud.com")
                .path("/fscmRestApi/resources/11.13.18.05/suppliers")
                .query("q=TaxRegistrationNumber={taxRegNumber}").buildAndExpand("9010156904");

        HttpEntity entity = new HttpEntity(headers);

        log.info("calling rest endpoint  " + uriComponents.toUriString());

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getInterceptors().add(new BasicAuthenticationInterceptor("exp.mdizdar", "bursa1234"));

        HttpEntity<Suppliers> response = restTemplate.exchange(
                uriComponents.toUriString(),
                HttpMethod.GET,
                entity,
                Suppliers.class);

        log.info(response.toString());

    }

    @Test
    public void getSupplierByTaxRegNumber() {
        Supplier supplier = supplierClient.getSupplierByTaxRegNumber(
                "ejwy-test.fa.em2.oraclecloud.com", "9010156904");

        assertNotNull(supplier);

        assertNotNull(supplier.getSupplierId());
        assertNotNull(supplier.getSupplierName());

        log.info(supplier.toString());
    }

    @Test(expected = RestClientException.class)
    public void getSupplierByTaxRegNumberNotExists() {
        supplierClient.getSupplierByTaxRegNumber(
                "ejwy-test.fa.em2.oraclecloud.com", "-1");
    }
}