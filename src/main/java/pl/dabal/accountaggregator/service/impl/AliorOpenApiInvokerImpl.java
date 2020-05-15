package pl.dabal.accountaggregator.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pl.dabal.accountaggregator.config.AliorProperties;
import pl.dabal.accountaggregator.model.json.AliorOpenApiRequest;
import pl.dabal.accountaggregator.model.json.AliorOpenApiResponse;
import pl.dabal.accountaggregator.service.AliorOpenApiInvoker;

import java.util.Map;

@Service
@Slf4j
@AllArgsConstructor
@Primary
public class AliorOpenApiInvokerImpl implements AliorOpenApiInvoker {


    private AliorProperties aliorProperties;

    @Override
    public AliorOpenApiResponse invoke(String url, AliorOpenApiRequest aliorOpenApiRequest, Map<String, String > headerMap) {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
        HttpHeaders headers = getHttpHeaders(headerMap);
        HttpEntity<AliorOpenApiRequest> request = new HttpEntity<>(aliorOpenApiRequest, headers);
        AliorOpenApiResponse response = restTemplate.postForObject(url, request, AliorOpenApiResponse.class);
        return response;
    }

    private HttpHeaders getHttpHeaders(Map<String, String > headerMap) {
        HttpHeaders headers = new HttpHeaders();
        headerMap.forEach((k,v) -> headers.add(k,v));

        return headers;
    }
}
