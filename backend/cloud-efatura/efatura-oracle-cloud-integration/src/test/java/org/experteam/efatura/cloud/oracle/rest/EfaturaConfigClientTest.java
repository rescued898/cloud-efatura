package org.experteam.efatura.cloud.oracle.rest;

import lombok.extern.slf4j.Slf4j;
import org.experteam.efatura.cloud.oracle.config.RestTemplateConfig;
import org.experteam.efatura.cloud.oracle.domain.EfaturaConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertNotNull;

@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = RestTemplateConfig.class)
@TestPropertySource(properties = {
        "client.user.name=exp.mdizdar",
        "client.user.password=bursa1234",
})
public class EfaturaConfigClientTest {

    @Autowired
    private EfaturaConfigClient efaturaConfigClient;

    @Test
    public void getEfaturaConfig() {
        EfaturaConfig efaturaConfig = efaturaConfigClient.getEfaturaConfig("ejwy-test.fa.em2.oraclecloud.com");

        assertNotNull(efaturaConfig);

        assertNotNull(efaturaConfig.getSource());
        assertNotNull(efaturaConfig.getOrgId());
        assertNotNull(efaturaConfig.getOperatingUnit());

        log.info(efaturaConfig.toString());
    }
}