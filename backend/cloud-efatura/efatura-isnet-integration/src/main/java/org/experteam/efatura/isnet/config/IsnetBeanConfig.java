package org.experteam.efatura.isnet.config;

import org.experteam.efatura.isnet.service.impl.InvoiceServiceImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

@Configuration
public class IsnetBeanConfig {

    @Value("${client.default-uri}")
    private String defaultUri;

    @Bean
    public Jaxb2Marshaller marshaller() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setContextPath("org.tempuri");
        return marshaller;
    }

    @Bean
    public InvoiceServiceImpl invoiceServiceClient(Jaxb2Marshaller marshaller) {
        InvoiceServiceImpl client = new InvoiceServiceImpl();
        client.setDefaultUri(defaultUri);
        client.setMarshaller(marshaller);
        client.setUnmarshaller(marshaller);
        return client;
    }
}
