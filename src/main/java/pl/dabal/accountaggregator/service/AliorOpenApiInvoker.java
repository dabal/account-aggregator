package pl.dabal.accountaggregator.service;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import pl.dabal.accountaggregator.model.User;
@Service
public interface AliorOpenApiInvoker {
    public String invoke(@AuthenticationPrincipal User user, String requestId, Object json);
}
