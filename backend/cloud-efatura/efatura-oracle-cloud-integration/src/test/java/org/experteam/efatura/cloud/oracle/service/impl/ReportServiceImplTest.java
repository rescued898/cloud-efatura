package org.experteam.efatura.cloud.oracle.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.experteam.efatura.cloud.oracle.config.OracleCloudBeanConfig;
import org.experteam.efatura.cloud.oracle.domain.SupplierDetailItem;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertNotNull;

@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = OracleCloudBeanConfig.class)
@TestPropertySource(properties = {
        "client.user.name=exp.mdizdar",
        "client.user.password=bursa1234",
        "client.report-service.supplier-dtl-path=/ISBANK/Efatura/SupplierDetails.xdo"
})
public class ReportServiceImplTest {

    @Autowired
    private ReportServiceImpl reportService;

    @Test
    public void getSupplierDetail() {
        SupplierDetailItem supplierDetailItem = reportService.getSupplierDetail(
                "https://ejwy-test.fa.em2.oraclecloud.com/xmlpserver/services/v2/ReportService", "300000002357774", "TRY");

        assertNotNull(supplierDetailItem);
        assertNotNull(supplierDetailItem.getSupplierId());
        assertNotNull(supplierDetailItem.getPartyId());
        assertNotNull(supplierDetailItem.getPartySiteId());
        assertNotNull(supplierDetailItem.getPaymentMethod());

    }
}