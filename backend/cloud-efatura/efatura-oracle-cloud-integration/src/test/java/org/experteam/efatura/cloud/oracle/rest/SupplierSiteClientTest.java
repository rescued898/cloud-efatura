package org.experteam.efatura.cloud.oracle.rest;

import lombok.extern.slf4j.Slf4j;
import org.experteam.efatura.cloud.oracle.config.RestTemplateConfig;
import org.experteam.efatura.cloud.oracle.domain.SupplierSite;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestClientException;

import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = RestTemplateConfig.class)
@TestPropertySource(properties = {
        "client.user.name=exp.mdizdar",
        "client.user.password=bursa1234",
})
public class SupplierSiteClientTest {

    @Autowired
    private SupplierSiteClient supplierSiteClient;

    @Test
    public void getSupplierSites() {
        List<SupplierSite> supplierSites = supplierSiteClient.getSupplierSites(
                "ejwy-test.fa.em2.oraclecloud.com", 300000002358291L);

        assertTrue(supplierSites.size() > 0);
        for (SupplierSite supplierSite : supplierSites) {
            assertNotNull(supplierSite.getSupplierSiteId());
            assertNotNull(supplierSite.getSupplierSiteCode());

            log.info(supplierSite.toString());
        }
    }

    @Test(expected = RestClientException.class)
    public void getSupplierSitesNotExists() {
        supplierSiteClient.getSupplierSites(
                "ejwy-test.fa.em2.oraclecloud.com", -1L);
    }
}