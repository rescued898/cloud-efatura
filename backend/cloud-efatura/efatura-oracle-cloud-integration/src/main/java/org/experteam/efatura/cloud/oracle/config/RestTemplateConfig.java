package org.experteam.efatura.cloud.oracle.config;

import org.experteam.efatura.cloud.oracle.rest.EfaturaConfigClient;
import org.experteam.efatura.cloud.oracle.rest.SupplierClient;
import org.experteam.efatura.cloud.oracle.rest.SupplierSiteClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.support.BasicAuthenticationInterceptor;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {

    @Value("${client.user.name}")
    private String userName;

    @Value("${client.user.password}")
    private String userPassword;


    @Bean
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplateBuilder().build();
        restTemplate.getInterceptors().add(new BasicAuthenticationInterceptor(userName, userPassword));
        return restTemplate;
    }

    @Bean
    public SupplierClient supplierController() {
        return new SupplierClient();
    }

    @Bean
    public SupplierSiteClient supplierSiteController() {
        return new SupplierSiteClient();
    }

    @Bean
    public EfaturaConfigClient efaturaConfigController() {
        return new EfaturaConfigClient();
    }
}
