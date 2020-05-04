package pl.dabal.accountaggregator.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pl.dabal.accountaggregator.config.AliorProperties;
import pl.dabal.accountaggregator.model.User;
import pl.dabal.accountaggregator.model.pojo.json.AliorOpenApiRequest;
import pl.dabal.accountaggregator.model.pojo.json.AuthAuthorizeResponse;

import java.net.http.HttpClient;

@Service
@Slf4j
@AllArgsConstructor
@Primary
public class AliorOpenApiInvokerRestTemplateImpl implements AliorOpenApiInvoker {


    private AliorProperties aliorProperties;
    private ObjectMapper objectMapper;
    private HttpClient httpClient;

    @Override
    public String invoke(User user, String requestId, Object json) {


        RestTemplate restTemplate = new RestTemplate();

        restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        headers.add("x-ibm-client-id", aliorProperties.getClientId());
        headers.add("x-ibm-client-secret", aliorProperties.getClientSecret());
        headers.add("x-jws-signature", "");
        headers.add("x-request-id", requestId);
        headers.add("Accept-Charset", "utf-8");
        headers.add("Accept-Encoding", "deflate");
        headers.add("accept", "application/json");


        HttpEntity<AliorOpenApiRequest> request = new HttpEntity<>((AliorOpenApiRequest) json, headers);
        AuthAuthorizeResponse response = restTemplate.postForObject("https://gateway.developer.aliorbank.pl/openapipl/sb/v2_1_1.1/auth/v2_1_1.1/authorize", request, AuthAuthorizeResponse.class);

        return (response.getAspspRedirectUri());
    }
}
