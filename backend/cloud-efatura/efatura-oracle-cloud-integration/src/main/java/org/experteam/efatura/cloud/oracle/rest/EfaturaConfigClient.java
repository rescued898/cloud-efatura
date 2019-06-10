package org.experteam.efatura.cloud.oracle.rest;

import lombok.extern.slf4j.Slf4j;
import org.experteam.efatura.cloud.oracle.domain.EfaturaConfig;
import org.experteam.efatura.cloud.oracle.domain.EfaturaConfigs;
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
public class EfaturaConfigClient {
    private final String configPath = "/fscmRestApi/resources/11.13.18.05/EfaturaConfig_c";

    @Autowired
    private RestTemplate restTemplate;

    public EfaturaConfig getEfaturaConfig(String host) throws RestClientException {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "application/json");

        UriComponents uriComponents = UriComponentsBuilder.newInstance()
                .scheme("https").host(host)
                .path(configPath)
                .buildAndExpand();

        HttpEntity entity = new HttpEntity(headers);

        log.info("calling suppliers REST API endpoint  " + uriComponents.toUriString());

        HttpEntity<EfaturaConfigs> response = restTemplate.exchange(
                uriComponents.toUriString(),
                HttpMethod.GET,
                entity,
                EfaturaConfigs.class);

        if (response.getBody().getItems().size() < 1) {
            throw new RestClientException("Konfigurasyon dosyasi bulunamadi.");
        }

        List<EfaturaConfig> efaturaConfigs = response.getBody().getItems().stream()
                .distinct()
                .collect(Collectors.toList());

        if (efaturaConfigs.size() > 1) {
            for (EfaturaConfig efaturaConfig : efaturaConfigs)
                log.error(efaturaConfig.toString());

            throw new RestClientException(" Birden fazla konfigurasyon dosyasi bulundu.");
        }

        return efaturaConfigs.get(0);
    }
}
