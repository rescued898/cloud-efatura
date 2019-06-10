package org.experteam.efatura.cloud.oracle.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.experteam.efatura.cloud.oracle.service.impl.InvoiceServiceImpl;
import org.experteam.efatura.cloud.oracle.service.impl.ReportServiceImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.ws.transport.http.HttpComponentsMessageSender;

@Slf4j
@Configuration
public class OracleCloudBeanConfig {

    @Value("${client.invoice-service.default-uri}")
    private String invoiceServiceDefaultUri;

    @Value("${client.report-service.default-uri}")
    private String reportServiceDefaultUri;

    @Value("${client.user.name}")
    private String userName;

    @Value("${client.user.password}")
    private String userPassword;

    @Bean
    public Jaxb2Marshaller marshaller() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setContextPaths(
                "com.oracle.xmlns.apps.financials.payables.invoices.quickinvoices.invoiceinterfaceservice.types",
                "com.oracle.xmlns.oxp.service.v2");
        return marshaller;
    }

    @Bean
    public InvoiceServiceImpl invoiceServiceClient(Jaxb2Marshaller marshaller) {
        InvoiceServiceImpl client = new InvoiceServiceImpl();
        client.setDefaultUri(invoiceServiceDefaultUri);
        client.setMarshaller(marshaller);
        client.setUnmarshaller(marshaller);
        // set a HttpComponentsMessageSender which provides support for basic authentication
        client.setMessageSender(httpComponentsMessageSender());

        return client;
    }

    @Bean
    public ReportServiceImpl reportServiceClient(Jaxb2Marshaller marshaller) {
        ReportServiceImpl client = new ReportServiceImpl();
        client.setDefaultUri(reportServiceDefaultUri);
        client.setMarshaller(marshaller);
        client.setUnmarshaller(marshaller);
        // set a HttpComponentsMessageSender which provides support for basic authentication
        client.setMessageSender(httpComponentsMessageSender());

        return client;
    }

    @Bean
    public HttpComponentsMessageSender httpComponentsMessageSender() {
        HttpComponentsMessageSender httpComponentsMessageSender = new HttpComponentsMessageSender();
        // set the basic authorization credentials
        httpComponentsMessageSender.setCredentials(usernamePasswordCredentials());

        return httpComponentsMessageSender;
    }

    @Bean
    public UsernamePasswordCredentials usernamePasswordCredentials() {
        // pass the user name and password to be used
        return new UsernamePasswordCredentials(userName, userPassword);
    }

}
