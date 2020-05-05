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
public interface AliorOpenApiInvoker {

    public AliorOpenApiResponse invoke(String url, AliorOpenApiRequest aliorOpenApiRequest) ;

}
