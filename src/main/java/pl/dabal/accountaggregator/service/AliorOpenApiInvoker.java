package pl.dabal.accountaggregator.service;

import org.springframework.stereotype.Service;
import pl.dabal.accountaggregator.model.json.AliorOpenApiRequest;
import pl.dabal.accountaggregator.model.json.AliorOpenApiResponse;

@Service
public interface AliorOpenApiInvoker {

    AliorOpenApiResponse invoke(String url, AliorOpenApiRequest aliorOpenApiRequest);

}
