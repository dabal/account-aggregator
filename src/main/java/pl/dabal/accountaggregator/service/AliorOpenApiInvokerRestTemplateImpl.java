package pl.dabal.accountaggregator.service;

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

@Service
@Slf4j
@AllArgsConstructor
@Primary
public class AliorOpenApiInvokerRestTemplateImpl  {


    private AliorProperties aliorProperties;


    public AliorOpenApiResponse invoke(String url, AliorOpenApiRequest aliorOpenApiRequest) {
        RestTemplate restTemplate = new RestTemplate();
       restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
        HttpHeaders headers = getHttpHeaders(aliorOpenApiRequest.getRequestHeader().getRequestId());
        HttpEntity<AliorOpenApiRequest> request = new HttpEntity<>(aliorOpenApiRequest, headers);
        AliorOpenApiResponse response = restTemplate.postForObject(url, request, AliorOpenApiResponse.class);
        return response;
    }

    private HttpHeaders getHttpHeaders(String requestId) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        headers.add("x-ibm-client-id", aliorProperties.getClientId());
        headers.add("x-ibm-client-secret", aliorProperties.getClientSecret());
        headers.add("x-jws-signature", "");
        headers.add("x-request-id", requestId);
        headers.add("Accept-Charset", "utf-8");
        headers.add("Accept-Encoding", "deflate");
        headers.add("accept", "application/json");
        return headers;
    }
}
