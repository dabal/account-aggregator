package pl.dabal.accountaggregator.service;

import org.springframework.stereotype.Service;
import pl.dabal.accountaggregator.model.pojo.json.AliorOpenApiResponse;

@Service
public interface AliorOpenApiInvoker {
    public AliorOpenApiResponse invoke(String url, String requestId, Object json);
}
